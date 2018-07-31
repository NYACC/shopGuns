package com.md.coupon.warpper;

import java.util.List;
import java.util.Map;

import com.md.coupon.constant.CodeStatus;
import com.md.coupon.factory.CouponFactory;
import com.md.goods.factory.PriceTagFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 优惠卷的包装类
 *
 * @authr
 * @date 2017年2月19日15:07:29
 */
public class CouponWarpper extends BaseControllerWarpper {

	public CouponWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		map.put("amountNum", CouponFactory.me().getNumByStatus((Long) map.get("id"), null));
		map.put("receivedNum", CouponFactory.me().getNumByStatus((Long) map.get("id"), CodeStatus.RECEIVED));
		map.put("unreceiveNum", CouponFactory.me().getNumByStatus((Long) map.get("id"), CodeStatus.CREATED));
		map.put("usedNum", CouponFactory.me().getNumByStatus((Long) map.get("id"), CodeStatus.USED));
		map.put("shopName", PriceTagFactory.me().getStoresName((Long) map.get("shopId")));
	}
}
