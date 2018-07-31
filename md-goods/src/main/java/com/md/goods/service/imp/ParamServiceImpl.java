package com.md.goods.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.ParamMapper;
import com.md.goods.factory.ParamFactory;
import com.md.goods.model.Param;
import com.md.goods.service.IParamService;

@Service
public class ParamServiceImpl extends ServiceImpl<ParamMapper, Param> implements IParamService {
	@Resource
	ParamMapper paramMapper;

	/**
	 * 查看参数组下是否有参数项true:有;false:没有
	 */
	@Override
	public boolean existItems(Long paramId) {
		Param goodsParam = paramMapper.selectById(paramId);
		if (goodsParam != null && goodsParam.getItemId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * 同分类下参数组名是否已经存在true:存在;false:不存在
	 */
	@Override
	public boolean existParamName(Long categoryId, String paramName) {
		Wrapper<Param> wrapper = new EntityWrapper<>();
		wrapper.eq("categoryId", categoryId);
		wrapper.eq("name", paramName);
		List<Map<String, Object>> result = this.paramMapper.selectMaps(wrapper);
		if (result != null && result.size() != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 同参数组下的参数项名是否已经存在
	 */
	@Override
	public boolean existItemsName(String itemName, Long paramId) {
		Param param = this.paramMapper.selectById(paramId);
		if (param != null && param.getItemId() != null
				&& ParamFactory.me().getItemsNames(param.getItemId()).indexOf(itemName) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 根据参数组名或分类查询参数组
	 */
	@Override
	public List<Map<String, Object>> find(Param param) {
		Wrapper<Param> wrapper = new EntityWrapper<>();
		if (param != null) {
			if (param.getCategoryId() != null && param.getCategoryId() != 0) {
				wrapper.eq("categoryId", param.getCategoryId());
			} else {
				wrapper.like("name", param.getName());
			}
		}
		wrapper.orderBy("num");
		List<Map<String, Object>> result = this.paramMapper.selectMaps(wrapper);
		return result;
	}

	/**
	 * 根据参数组编号查找参数
	 */
	@Override
	public Param findById(Long paramId) {
		return this.paramMapper.selectById(paramId);
	}

	/**
	 * 修改参数组
	 */
	@Override
	public void edit(Param goodsParam) {
		this.paramMapper.updateById(goodsParam);
	}

	/**
	 * 删除参数组
	 */
	@Override
	public void delete(Long paramId) {
		this.paramMapper.deleteById(paramId);
	}

	/**
	 * 根据cid查找参数组
	 */
	@Override
	public List<Param> findByCid(Long cid) {
		Wrapper<Param> wrapper = new EntityWrapper<>();
		wrapper.eq("categoryId", cid);
		List<Param> selectList = paramMapper.selectList(wrapper);
		return selectList;
	}

}
