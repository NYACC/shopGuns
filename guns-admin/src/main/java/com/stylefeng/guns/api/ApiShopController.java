package com.stylefeng.guns.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.goods.model.Shop;
import com.md.goods.service.IShopService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.MapUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 店铺接口
 * @author 54476
 *
 */

@Controller
@RequestMapping("/shop")
public class ApiShopController extends BaseController {

	@Resource
	IShopService shopService;

	@ApiOperation(value = "获取门店列表", notes = "获取门店列表")
	@RequestMapping(value = "/getShopList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getShopList(
			@ApiParam("proId") @RequestParam(value = "proId", required = true) @RequestBody long proId,
			@ApiParam("cityId") @RequestParam(value = "cityId", required = true) @RequestBody long cityId) {
		JSONObject jb = new JSONObject();
		Shop shop = new Shop();
		shop.setProvinceId(proId);
		if (cityId != 0) {
			shop.setCityId(cityId);
		}
		List<Map<String, Object>> list = shopService.find(shop);
		jb.put("list", list);
		return jb;
	}

	@SuppressWarnings("unused")
	@ApiOperation(value = "定位获取门店", notes = "定位获取门店")
	@RequestMapping(value = "/getShopByGPS", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getShopByGPS(
			@ApiParam("lng") @RequestParam(value = "经度", required = true) @RequestBody double lng,
			@ApiParam("lat") @RequestParam(value = "纬度", required = true) @RequestBody double lat) {
		JSONObject jb = new JSONObject();
		Shop shop = new Shop();
		List<Map<String, Object>> list = shopService.find(shop);

		if (list.size() > 0) {
			double distance = 0;
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					double temp;
					temp = MapUtil.getDistance(lat, lng, Double.parseDouble(list.get(i).get("lat").toString()),
							Double.parseDouble(list.get(i).get("lng").toString()));
					if(temp < distance) {
						distance = temp;
						jb.put("data", list.get(i));
					}
				} else {
					distance = MapUtil.getDistance(lat, lng, Double.parseDouble(list.get(i).get("lat").toString()),
							Double.parseDouble(list.get(i).get("lng").toString()));
					jb.put("data", list.get(i));
				}
			}
		}
		return jb;
	}

}
