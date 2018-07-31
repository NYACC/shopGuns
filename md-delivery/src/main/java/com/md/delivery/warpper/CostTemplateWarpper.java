package com.md.delivery.warpper;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 包装类
 *
 * @authr
 * @date 2017年2月19日15:07:29
 */
public class CostTemplateWarpper extends BaseControllerWarpper {

	public CostTemplateWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		map.put("isdelivery", "1");
		map.put("ykg", "0");
		map.put("startPrice", "0");
		map.put("addedWeight", "0");
		map.put("addedPrice", "0");
	}
}
