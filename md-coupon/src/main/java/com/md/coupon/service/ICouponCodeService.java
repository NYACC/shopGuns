package com.md.coupon.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.coupon.model.Coupon;
import com.md.coupon.model.CouponCode;

public interface ICouponCodeService extends IService<CouponCode> {

	/**
	 * 判断是否可以操作（修改和删除）
	 * 
	 * @param couponId
	 * @return
	 */
	Boolean isOperable(Long couponId);

	/**
	 * 领取优惠卷
	 * 
	 * @param memberId
	 * @return
	 */
	Boolean receive(Long couponId, Long memberId);

	/**
	 * 生产一张优惠码
	 * 
	 * @param couponId
	 * @param memberId
	 */
	void produce(CouponCode couponCode);

	/**
	 * 创建若干优惠码
	 * 
	 * @param coupon
	 * @param quantity
	 * @return
	 */
	List<Map<String, Object>> create(Coupon coupon, Integer quantity);
	
	/**
	 * 判断是否可以领取
	 * 
	 * @param couponId
	 * @return
	 */
	Boolean isReceive(Long couponId,Long memberId);
	
	/**
	 * 通过优惠码获取优惠码对象
	 * @param code
	 * @return
	 */
	CouponCode getByCode(String code);
}
