package com.md.goods.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.SpecificationItemMapper;
import com.md.goods.dao.SpecificationMapper;
import com.md.goods.exception.GoodsExceptionEnum;
import com.md.goods.model.Specification;
import com.md.goods.model.SpecificationItem;
import com.md.goods.service.ISpecificationService;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification>
		implements ISpecificationService {

	@Resource
	SpecificationMapper specificationMapper;
	@Resource
	SpecificationItemMapper specificationItemMapper;

	@Override
	public boolean checkNameOnCategory(Long categoryId, Long id, String name) {
		Wrapper<Specification> wrapper = new EntityWrapper<>();
		wrapper.eq("name", name);
		wrapper.eq("categoryId", categoryId);
		List<Specification> selectList = specificationMapper.selectList(wrapper);
		if (selectList.size() > 0) {
			// 如果名称相同，id不一致，证明已存在这个规格
			if (!selectList.get(0).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> findList(Specification queryObj) {
		Wrapper<Specification> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(queryObj.getCategoryId()) && queryObj.getCategoryId() != 0) {
			wrapper.eq("categoryId", queryObj.getCategoryId());
		}
		if (ToolUtil.isNotEmpty(queryObj.getName())) {
			wrapper.eq("name", queryObj.getName());
		}
		ArrayList<String> order = new ArrayList<>();
		order.add("categoryId");
		order.add("sequence");
		wrapper.orderAsc(order);
		return specificationMapper.selectMaps(wrapper);
	}

	@Override
	public void delete(Long id) {
		// 删除规格组下面的所有规格项
		Wrapper<SpecificationItem> wrapper = new EntityWrapper<>();
		wrapper.eq("pid", id);
		specificationItemMapper.delete(wrapper);
		// 删除规格组
		specificationMapper.deleteById(id);
	}

	@Override
	public Specification getById(Long id) {
		Specification selectById = specificationMapper.selectById(id);
		return selectById;
	}

	@Override
	public void add(Specification specification, Set<SpecificationItem> specificationItems) {
		// 添加规格组
		specificationMapper.insert(specification);
		// 添加规格项
		for (SpecificationItem specificationItem : specificationItems) {
			if (ToolUtil.isEmpty(specificationItem.getName())) {
				throw new GunsException(GoodsExceptionEnum.REQUEST_NULL);
			}
			specificationItem.setPid(specification.getId());
			specificationItemMapper.insert(specificationItem);
		}
	}

	@Override
	public void edit(Specification specification) {
		// 修改规格组
		specificationMapper.updateById(specification);
	}

	@Override
	public List<Specification> findByCid(Long cid) {
		Wrapper<Specification> wrapper = new EntityWrapper<>();
		wrapper.eq("categoryId", cid);
		List<Specification> selectList = specificationMapper.selectList(wrapper);
		return selectList;
	}

}
