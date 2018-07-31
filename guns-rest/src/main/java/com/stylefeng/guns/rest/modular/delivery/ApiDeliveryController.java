package com.stylefeng.guns.rest.modular.delivery;

import java.math.BigDecimal;
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

import com.md.delivery.model.DeliveryCost;
import com.md.delivery.service.IDeliveryCostService;
import com.md.delivery.service.IDeliveryModeService;
import com.md.goods.model.Shop;
import com.md.goods.service.IShopService;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/delivery")
public class ApiDeliveryController extends BaseController{

	@Resource
	IDeliveryModeService deliveryModeService;
	
	@Resource
	IDeliveryCostService deliveryCostService;
	
	@Resource
	IShopService shopService;
	
	@ApiOperation(value = "配送方式列表", notes = "配送方式列表")
	@RequestMapping(value = "/deliveryModeList", method = RequestMethod.POST)
	public ResponseEntity<?> deliveryModeList() {
		List<Map<String, Object>> list = deliveryModeService.list();
		return ResponseEntity.ok(list);
	}
	
	@ApiOperation(value = "获取运费", notes = "获取运费")
	@RequestMapping(value = "/getFreight", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getFreight(@ApiParam("物流方式id") @RequestParam(value = "modeId", required = true) @RequestBody Long modeId,
			@ApiParam("门店id") @RequestParam(value = "shopId", required = true) @RequestBody Long shopId,
			@ApiParam("订单总重量") @RequestParam(value = "weight", required = true) @RequestBody BigDecimal weight,
			@ApiParam("收货地址区域id") @RequestParam(value = "deliveryArea", required = true) @RequestBody Long deliveryArea) {
		Shop shop = shopService.findById(shopId);
		DeliveryCost deliveryCost = deliveryCostService.getCost(modeId, shop.getCountyId(), deliveryArea);
		BigDecimal freight;
		if(deliveryCost != null) {
			if(deliveryCost.getYkg().compareTo(weight) <= 0) {
				freight = deliveryCost.getStartPrice();
			}else {
				BigDecimal overWeight = weight.subtract(deliveryCost.getYkg());
				BigDecimal temp = overWeight.divide(deliveryCost.getAddedWeight(),2,BigDecimal.ROUND_HALF_UP);  
				if(temp.compareTo(new BigDecimal(temp.intValue())) > 0) {
					freight = deliveryCost.getStartPrice().add(new BigDecimal(temp.intValue()+1).multiply(deliveryCost.getAddedPrice()));
				}else {
					freight = deliveryCost.getStartPrice().add(new BigDecimal(temp.intValue()).multiply(deliveryCost.getAddedPrice()));
				}
			}
			return ResponseEntity.ok(freight);
		}else {
			return ResponseEntity.ok(0);
		}
	}
}
