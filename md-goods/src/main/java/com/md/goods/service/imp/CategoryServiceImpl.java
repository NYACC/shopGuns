package com.md.goods.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.CategoryMapper;
import com.md.goods.model.Category;
import com.md.goods.service.ICategoryService;
import com.stylefeng.guns.core.constant.CategoryStatus;
import com.stylefeng.guns.core.constant.Status;
import com.stylefeng.guns.core.node.ZTreeNode;

@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

	@Resource
	CategoryMapper categoryMapper;

	@Override
	public List<ZTreeNode> tree() {
		return categoryMapper.tree();
	}

	@Override
	public Category getById(Long id) {
		return categoryMapper.selectById(id);
	}

	@Override
	public Boolean checkNameExist(Long id, String checkName) {
		Wrapper<Category> wrapper = new EntityWrapper<Category>();
		wrapper.eq("name", checkName);
		List<Category> selectList = categoryMapper.selectList(wrapper);
		if (selectList.size() > 0) {
			// 如果名称相同，id不相同，就是表示该名称已经被使用
			if (!selectList.get(0).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void disable(Long id) {
		// 修改编号=id的分类状态为停用
		Category selectById = categoryMapper.selectById(id);
		selectById.setStatus(Status.DISABLE.getCode());
		categoryMapper.updateById(selectById);
		// 查询出该分类的子分类
		List<Category> sonList = this.findSonList(id);
		// 迭代停用子分类
		for (Category category : sonList) {
			this.disable(category.getId());
		}
	}

	@Override
	public void enable(Long id) {
		// 修改编号=id的分类状态为启用
		Category selectById = categoryMapper.selectById(id);
		selectById.setStatus(Status.ENABLED.getCode());
		categoryMapper.updateById(selectById);
		// 查询出该分类的子分类
		List<Category> sonList = this.findSonList(id);
		// 迭代启用子分类
		for (Category category : sonList) {
			this.enable(category.getId());
		}

	}

	@Override
	public void edit(Category category) {
		categoryMapper.updateById(category);
	}

	@Override
	public void add(Category category) {
		categoryMapper.insert(category);
	}

	@Override
	public List<Category> findSonList(Long id) {
		Wrapper<Category> wrapper = new EntityWrapper<Category>();
		wrapper.eq("pid", id);
		List<Category> selectList = categoryMapper.selectList(wrapper);
		return selectList;
	}

	@Override
	public List<Map<String, Object>> findSonMaps(Long id) {
		Wrapper<Category> wrapper = new EntityWrapper<Category>();
		wrapper.eq("pid", id);
		wrapper.orderBy("num", true);
		List<Map<String, Object>> selectMaps = categoryMapper.selectMaps(wrapper);
		return selectMaps;
	}

	@Override
	public List<Category> findCategoryList(Long id) {
		Wrapper<Category> wrapper = new EntityWrapper<Category>();
		wrapper.eq("pid", id);
		wrapper.eq("status", CategoryStatus.ENABLE.getCode());
		wrapper.orderBy("num", true);
		List<Category> selectList = categoryMapper.selectList(wrapper);
		return selectList;
	}


}
