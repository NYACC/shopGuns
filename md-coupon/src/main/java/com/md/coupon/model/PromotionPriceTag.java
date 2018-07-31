package com.md.coupon.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_promotion_pricetag")
public class PromotionPriceTag {
	/**
	 * 主键id创建映射
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	private Long promotionId;
	private Long priceTagId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public Long getPriceTagId() {
		return priceTagId;
	}

	public void setPriceTagId(Long priceTagId) {
		this.priceTagId = priceTagId;
	}

	public PromotionPriceTag() {
		super();
	}

	public PromotionPriceTag(Long promotionId, Long priceTagId) {
		super();
		this.promotionId = promotionId;
		this.priceTagId = priceTagId;
	}
}
