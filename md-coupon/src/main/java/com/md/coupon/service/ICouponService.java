package com.md.coupon.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.coupon.model.Coupon;

public interface ICouponService extends IService<Coupon> {

	/**
	 * 获取优惠卷列表
	 * 
	 * @param coupon
	 * @return
	 */
	List<Map<String, Object>> find(Coupon coupon);

	/**
	 * 添加优惠卷
	 * 
	 * @param coupon
	 */
	void add(Coupon coupon);

	/**
	 * 通过id获取优惠卷对象
	 * 
	 * @param couponId
	 * @return
	 */
	Coupon getById(Long couponId);

	/**
	 * 修改优惠卷
	 * 
	 * @param coupon
	 */
	void update(Coupon coupon);

	/**
	 * 删除
	 * 
	 * @param couponId
	 */
	void deleteById(Long couponId);

	/**
	 * 获取当前门店有效优惠券
	 * @param shopId
	 * @return
	 */
	List<Map<String, Object>> getList(Long shopId);
	
	/**
	 * 获取我的未使用优惠券
	 * @param memberId
	 * @return
	 */
	List<Map<String, Object>> getMyCouponList(Long memberId);
	
}
