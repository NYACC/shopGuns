package com.md.goods.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.SpecificationItemMapper;
import com.md.goods.model.SpecificationItem;
import com.md.goods.service.ISpecificationItemService;

@Service
public class SpecificationItemServiceImpl extends ServiceImpl<SpecificationItemMapper, SpecificationItem> implements ISpecificationItemService {

	@Resource
	SpecificationItemMapper specificationItemMapper;
	
	@Override
	public boolean checkNameOnSpecification(Long pid, Long id, String name) {
		Wrapper<SpecificationItem> wrapper = new EntityWrapper<>();
		wrapper.eq("name", name);
		wrapper.eq("pid", pid);
		List<SpecificationItem> selectList = specificationItemMapper.selectList(wrapper);
		if (selectList.size()>0) {
			if (!selectList.get(0).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<SpecificationItem> getByPid(Long pid) {
		Wrapper<SpecificationItem> wrapper = new EntityWrapper<>();
		wrapper.eq("pid", pid);
		wrapper.orderBy("sequence", true);
		List<SpecificationItem> selectList = specificationItemMapper.selectList(wrapper);
		return selectList;
	}

	@Override
	public Long add(SpecificationItem specificationItem) {
		specificationItemMapper.insert(specificationItem);
		return specificationItem.getId();
	}

	@Override
	public void delete(Long id) {
		specificationItemMapper.deleteById(id);
	}

	@Override
	public void update(SpecificationItem specificationItem) {
		specificationItemMapper.updateById(specificationItem);
	}

}
