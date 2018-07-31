package com.md.delivery.warpper;

import java.util.List;
import java.util.Map;

import com.md.delivery.factory.DeliveryCostFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 包装类
 *
 * @authr
 * @date 2017年2月19日15:07:29
 */
public class DeliveryCostWarpper extends BaseControllerWarpper {

	public DeliveryCostWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		map.put("areaName", DeliveryCostFactory.me().getAreaName((Long) map.get("areaId")));
		map.put("deliveryAreaName", DeliveryCostFactory.me().getAreaName((Long) map.get("deliveryArea")));
		map.put("isdelivery", map.get("isdelivery").toString().equals("0")?"否":"是");
	}
}
