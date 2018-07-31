package com.stylefeng.guns.rest.modular.coupon.dto;

/**
 * 优惠券
 * 
 * @author 54476
 *
 */
public class CouponRequest {

	private Long couponId;

	private Long memberId;

	private Long shopId;

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

}
