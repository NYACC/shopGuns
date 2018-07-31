package com.md.coupon.warpper;

import java.util.List;
import java.util.Map;

import com.md.coupon.factory.CouponCodeFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 优惠码的包装类
 *
 * @authr
 * @date 2017年2月19日15:07:29
 */
public class CouponCodeWarpper extends BaseControllerWarpper {

	public CouponCodeWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		map.put("couponName", CouponCodeFactory.me().getName((Long) map.get("couponId")));
		map.put("useStart", CouponCodeFactory.me().getUseStart((Long) map.get("couponId")));
		map.put("useEnd", CouponCodeFactory.me().getUseEnd((Long) map.get("couponId")));
		map.put("couponContent", CouponCodeFactory.me().getContent((Long) map.get("couponId")));
		map.put("remark", CouponCodeFactory.me().getRemark((Long) map.get("couponId")));
	}
}
