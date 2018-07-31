package com.stylefeng.guns.rest.modular.settle;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.md.delivery.model.DeliveryMode;
import com.md.member.model.Address;
import com.md.order.model.Order;
import com.md.order.model.ShopItem;
import com.md.settlement.service.IAccountService;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.settle.dto.SettleRequest;
import com.stylefeng.guns.rest.modular.settle.dto.ShopCoupon;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 结算
 * @author 54476
 *
 */
@RestController
@RequestMapping("/settlement")
public class ApiSettlementController {

	@Resource
	IAccountService accountService;
	
    /**
     * 结算
     */
	@ApiOperation(value = "结算", notes = "结算")
    @RequestMapping(value = "/account", method = { RequestMethod.POST })
    public ResponseEntity<?> orderItemSubmit(@RequestBody SettleRequest settleRequest
    		) {
    	//判断库存是否充足
    	if(!accountService.inventoryEnough(settleRequest.getShopItems(), accountService.findPriceTag(settleRequest.getShopItems()))){
    		throw new GunsException(BizExceptionEnum.INVENTORY_NOENOUGH);
    	}
    	
    	Map<Long, List<ShopItem>> map = new HashMap<>();
    	
    	for(ShopItem shopItem : settleRequest.getShopItems()){
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
    		for(ShopCoupon sCoupon :settleRequest.getShopCouponList()){
    			if(sCoupon.getShopId() == entry.getKey()){
    				couponCode = sCoupon.getCouponCode();
    			}
    		}
    		Order order = accountService.amount(entry.getKey(),entry.getValue(),settleRequest.getDeliveryMode(),settleRequest.getAddress(),couponCode);
    		orders.add(order);
    	}

        return ResponseEntity.ok(orders);
    }
}
