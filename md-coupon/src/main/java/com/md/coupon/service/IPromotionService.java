package com.md.coupon.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.coupon.model.Promotion;

public interface IPromotionService extends IService<Promotion> {

	/**
	 * 添加促销
	 * 
	 * @param promotion
	 */
	void add(Promotion promotion);

	/**
	 * 获取促销列表
	 * 
	 * @param promotion
	 * @return
	 */
	List<Map<String, Object>> find(Promotion promotion);

	/**
	 * 通过id获取促销
	 * 
	 * @param promtionId
	 * @return
	 */
	Promotion getById(Long promtionId);

	/**
	 * 修改
	 * 
	 * @param promotion
	 */
	void update(Promotion promotion);

	/**
	 * 是否可以操作
	 * 
	 * @param id
	 * @return
	 */
	Boolean isOperable(Long id);

	/**
	 * 删除
	 * 
	 * @param promotionId
	 */
	void deleteById(Long promotionId);

	/**
	 * 绑定价格签关联
	 * 
	 * @param promotionId
	 * @param addTags
	 */
	void bindPriceTag(Long promotionId, Long addTags);

	/**
	 * 解除绑定
	 * 
	 * @param promotionId
	 * @param priceTagId
	 */
	void unbindPriceTag(Long promotionId, Long priceTagId);

	/**
	 * 
	 * @param priceTagId
	 * @param promotion
	 * @return
	 */
	boolean allowBind(Long priceTagId, Promotion promotion);

}
