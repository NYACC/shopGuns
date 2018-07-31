package com.stylefeng.guns.rest.modular.shop;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.md.goods.model.Shop;
import com.md.goods.service.IShopService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.rest.modular.shop.dto.ShopRequest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 店铺接口
 * @author 54476
 *
 */

@RestController
@RequestMapping("/shop")
public class ApiShopController extends BaseController {

	@Resource
	IShopService shopService;

	@ApiOperation(value = "获取门店列表", notes = "获取门店列表")
	@RequestMapping(value = "/getShopList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getShopList(
			@ApiParam("proId") @RequestParam(value = "proId", required = true) @RequestBody long proId,
			@ApiParam("cityId") @RequestParam(value = "cityId", required = true) @RequestBody long cityId) {
		Shop shop = new Shop();
		shop.setProvinceId(proId);
		if (cityId != 0) {
			shop.setCityId(cityId);
		}
		List<Map<String, Object>> list = shopService.find(shop);
		return ResponseEntity.ok(list);
	}

	@ApiOperation(value = "定位获取门店", notes = "定位获取门店")
	@RequestMapping(value = "/getShopByGPS", method = RequestMethod.POST)
	public ResponseEntity<?> getShopByGPS(@RequestBody ShopRequest shopRequest) {
		Shop shop = new Shop();
		List<Map<String, Object>> list = shopService.find(shop);

		if (list.size() > 0) {
			double distance = 0;
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					double temp;
					temp = MapUtil.getDistance(shopRequest.getLat(), shopRequest.getLng(), Double.parseDouble(list.get(i).get("lat").toString()),
							Double.parseDouble(list.get(i).get("lng").toString()));
					if(temp < distance) {
						distance = temp;
						return ResponseEntity.ok(list.get(i));
					}
				} else {
					distance = MapUtil.getDistance(shopRequest.getLat(), shopRequest.getLng(), Double.parseDouble(list.get(i).get("lat").toString()),
							Double.parseDouble(list.get(i).get("lng").toString()));
					return ResponseEntity.ok(list.get(i));
				}
			}
		}
		return ResponseEntity.ok("");
	}

}
