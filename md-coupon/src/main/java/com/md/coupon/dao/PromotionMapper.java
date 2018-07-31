package com.md.coupon.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.md.coupon.model.Promotion;

public interface PromotionMapper extends BaseMapper<Promotion> {
	/**
	 * 获取商品已参加过且为结束的促销
	 * 
	 * @param priceTagId
	 * @return
	 */
	List<Promotion> findJionNoEnd(@Param("priceTagId")Long priceTagId);
}
