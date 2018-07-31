package com.md.goods.service;

import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.TagRelation;

/**
 * 商品和标签关联的service
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
public interface ITagRelationService extends IService<TagRelation> {

	/**
	 * 添加商品和标签的关联
	 * 
	 * @param
	 * @return
	 */
	void add(Set<TagRelation> tagRelationSet);

	/**
	 * 修改商品和标签的关联
	 * 
	 * @param
	 * @return
	 */
	void edit(Long goodsId, Set<TagRelation> tagRelationSet);
}
