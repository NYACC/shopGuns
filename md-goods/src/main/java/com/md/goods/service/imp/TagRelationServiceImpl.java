package com.md.goods.service.imp;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.TagRelationMapper;
import com.md.goods.model.TagRelation;
import com.md.goods.service.ITagRelationService;

@Service
@Transactional
public class TagRelationServiceImpl extends ServiceImpl<TagRelationMapper, TagRelation> implements ITagRelationService {
	@Resource
	TagRelationMapper tagRelationMapper;

	/**
	 * 添加商品和分类关联
	 */
	@Override
	public void add(Set<TagRelation> tagRelationSet) {
		for (TagRelation tagRelation : tagRelationSet) {
			tagRelationMapper.insert(tagRelation);
		}
	}

	@Override
	public void edit(Long goodsId, Set<TagRelation> tagRelationSet) {
		Wrapper<TagRelation> wrapper = new EntityWrapper<>();
		wrapper.eq("goodsId", goodsId);
		tagRelationMapper.delete(wrapper);
		this.add(tagRelationSet);
	}
}
