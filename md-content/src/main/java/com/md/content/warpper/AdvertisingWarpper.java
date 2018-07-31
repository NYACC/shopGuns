package com.md.content.warpper;

import java.util.List;
import java.util.Map;

import com.md.content.factory.AdvertisingFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 广告的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class AdvertisingWarpper extends BaseControllerWarpper {

	public AdvertisingWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		map.put("positionName", AdvertisingFactory.me().getPositionName((Long) map.get("positionId")));
	}

}
