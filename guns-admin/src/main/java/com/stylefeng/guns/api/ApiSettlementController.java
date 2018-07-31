package com.stylefeng.guns.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.delivery.model.DeliveryMode;
import com.md.member.model.Address;
import com.md.order.model.Order;
import com.md.order.model.ShopItem;
import com.md.settlement.service.IAccountService;
import com.stylefeng.guns.api.dto.ShopCoupon;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 结算
 * @author 54476
 *
 */
@Controller
@RequestMapping("/settlement")
public class ApiSettlementController extends BaseController{

	@Resource
	IAccountService accountService;
	
    /**
     * 结算
     */
	@ApiOperation(value = "结算", notes = "结算")
    @RequestMapping(value = "/account", method = { RequestMethod.POST })
    @ResponseBody
	@ApiImplicitParams({
		@ApiImplicitParam(name = "shopItems", value = "商品项", required = true, dataType = "List<ShopItem>", paramType = "body"),
		@ApiImplicitParam(name = "address", value = "默认地址", required = true, dataType = "Address", paramType = "body"),
		@ApiImplicitParam(name = "deliveryMode", value = "商品项", required = true, dataType = "DeliveryMode", paramType = "body"),
		@ApiImplicitParam(name = "shopCouponList", value = "优惠券", required = true, dataType = "List<ShopCoupon>", paramType = "body")
	})
    public Object orderItemSubmit(List<ShopItem> shopItems,Address address,DeliveryMode deliveryMode, List<ShopCoupon> shopCouponList) {
    	//判断库存是否充足
    	if(!accountService.inventoryEnough(shopItems, accountService.findPriceTag(shopItems))){
    		throw new GunsException(BizExceptionEnum.INVENTORY_NOENOUGH);
    	}
    	
    	Map<Long, List<ShopItem>> map = new HashMap<>();
    	
    	for(ShopItem shopItem : shopItems){
    		if(map.containsKey(shopItem.getShopId())){
    			map.get(shopItem.getShopId()).add(shopItem);
    		}else{
    			List<ShopItem> tempShopItem =  new ArrayList<ShopItem>();
    			tempShopItem.add(shopItem);
    			map.put(shopItem.getShopId(),tempShopItem);
    		}
    	}	
    	List<Order> orders = new ArrayList<>();
    	
    	for(Map.Entry<Long, List<ShopItem>> entry : map.entrySet()){
    		String couponCode = null;
    		for(ShopCoupon sCoupon :shopCouponList){
    			if(sCoupon.getShopId() == entry.getKey()){
    				couponCode = sCoupon.getCouponCode();
    			}
    		}
    		Order order = accountService.amount(entry.getKey(),entry.getValue(),deliveryMode,address,couponCode);
    		orders.add(order);
    	}

        return orders;
    }
}
