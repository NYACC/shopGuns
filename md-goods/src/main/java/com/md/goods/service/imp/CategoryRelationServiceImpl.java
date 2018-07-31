package com.md.goods.service.imp;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.CategoryRelationMapper;
import com.md.goods.model.CategoryRelation;
import com.md.goods.service.ICategoryRelationService;

@Service
@Transactional
public class CategoryRelationServiceImpl extends ServiceImpl<CategoryRelationMapper, CategoryRelation>
		implements ICategoryRelationService {
	@Resource
	CategoryRelationMapper categoryRelationMapper;

	@Override
	public void add(Set<CategoryRelation> categoryRelationSet) {
		for (CategoryRelation categoryRelation : categoryRelationSet) {
			categoryRelationMapper.insert(categoryRelation);
		}
	}

	@Override
	public void edit(Long goodsId, Set<CategoryRelation> categoryRelationSet) {
		Wrapper<CategoryRelation> wrapper = new EntityWrapper<>();
		wrapper.eq("goodsId", goodsId);
		categoryRelationMapper.delete(wrapper);
		this.add(categoryRelationSet);
	}
}
