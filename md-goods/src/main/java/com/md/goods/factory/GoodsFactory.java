package com.md.goods.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.md.goods.dao.CategoryRelationMapper;
import com.md.goods.dao.GoodsMapper;
import com.md.goods.dao.TagRelationMapper;
import com.md.goods.model.CategoryRelation;
import com.md.goods.model.Goods;
import com.md.goods.model.TagRelation;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 商品创建工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class GoodsFactory {
	private GoodsMapper goodsMapper=SpringContextHolder.getBean(GoodsMapper.class);
	private CategoryRelationMapper categoryRelationMapper=SpringContextHolder.getBean(CategoryRelationMapper.class);
	private TagRelationMapper tagRelationMapper=SpringContextHolder.getBean(TagRelationMapper.class);
	public static GoodsFactory me() {
        return SpringContextHolder.getBean(GoodsFactory.class);
    }

	/**
	 * 根据商品编号获取分类编号
	 * @param goodsId
	 * @return
	 */
	public List<Long> getCategoryIds(Long goodsId){
		Wrapper<CategoryRelation> wrapper=new EntityWrapper<>();
		wrapper.eq("goodsId",goodsId);
		List<CategoryRelation> relationList=categoryRelationMapper.selectList(wrapper);
		List<Long> list=new ArrayList<>();
		for(CategoryRelation categoryRelation:relationList){
			list.add(categoryRelation.getCategoryId());
		}
		return list;
	}
	public List<Long> getTagIds(Long goodsId){
		Wrapper<TagRelation> wrapper=new EntityWrapper<>();
		wrapper.eq("goodsId",goodsId);
		List<TagRelation> tagList=tagRelationMapper.selectList(wrapper);
		List<Long> tagIds=new ArrayList<>();
		tagList.stream().forEach(TagRelation->{tagIds.add(TagRelation.getTagId());});
		return tagIds;
	}
	
	public String getNameById(Long id){
		Goods goods = goodsMapper.selectById(id);
		return goods.getName();
	}
	
	public List<Map<String,Object>> goodsList(String shopIds, Long categoryId, Long brandId,String goodsName){
		return this.goodsMapper.goodsList(shopIds, categoryId, brandId, goodsName);
	}
	 
}
