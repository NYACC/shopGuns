package com.md.coupon.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.md.coupon.model.PromotionPriceTag;

public interface IPromotionPriceTagService extends IService<PromotionPriceTag> {

	/**
	 * 根据促销获得价格签集合
	 * 
	 * @param promotionId
	 * @return
	 */
	List<Long> findPriceTagIds(Long promotionId);

	/**
	 * 根据促销id删除关联
	 * 
	 * @param promotionId
	 */
	void deleteByPromotionId(Long promotionId);

	/**
	 * 根据价格签获取促销集合
	 * 
	 * @param priceTagId
	 * @param promotionId
	 * @return
	 */
	List<Long> findPromotionIds(Long priceTagId);

}
