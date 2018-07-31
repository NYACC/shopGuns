package com.md.coupon.warpper;

import java.util.List;
import java.util.Map;

import com.md.coupon.constant.PromotionStatus;
import com.md.goods.factory.ShopFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 优惠卷的包装类
 *
 * @authr
 * @date 2017年2月19日15:07:29
 */
public class PromotionWarpper extends BaseControllerWarpper {

	public PromotionWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		map.put("statusName", PromotionStatus.valueOf((Integer) map.get("status")));
		map.put("shopName", ShopFactory.me().getShopName((Long)map.get("shopId")));
	}
}
