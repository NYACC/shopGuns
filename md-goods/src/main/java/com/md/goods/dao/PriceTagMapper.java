package com.md.goods.dao;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.md.goods.model.PriceTag;

/**
 * <p>
  * 价格标签 Mapper 接口
 * </p>
 *
 * @author 
 * @since 
 */
public interface PriceTagMapper extends BaseMapper<PriceTag> {

	Long getSumByStock(@Param("goodsId")Long goodsId, @Param("shopId")Long shopId);
	
}