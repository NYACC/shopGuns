package com.stylefeng.guns.modular.system.controller.order;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONArray;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IShopService;
import com.md.member.service.IMemberService;
import com.md.order.model.Order;
import com.md.order.model.ShopItem;
import com.md.settlement.service.IAccountService;
import com.stylefeng.guns.common.annotion.OrderLog;
import com.stylefeng.guns.common.constant.dictmap.order.OrderDict;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;

/**
 * 结算中心控制器
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {
	@Resource
	private IPriceTagService priceTagService;
	@Resource
	private IShopService shopService;
	@Resource
	private IAccountService accountService;
	@Resource
	private IMemberService memberService;

    /**
     * 结算
     */
    @OrderLog(value = "添加订单", key = "sn", dict = OrderDict.class)
    @RequestMapping("/shopListSubmit")
    @ResponseBody
    public Object orderItemSubmit(String shopItemsJson,Long shopId,Long customerId,String sn,Model model) {
    	List<ShopItem> shopItems = JSONArray.parseArray(HtmlUtils.htmlUnescape(shopItemsJson),ShopItem.class);
    	//判断库存是否充足
    	if(!accountService.inventoryEnough(shopItems, accountService.findPriceTag(shopItems))){
    		throw new GunsException(BizExceptionEnum.INVENTORY_NOENOUGH);
    	}
    	//结算
    	Order order = accountService.amount(shopId,shopItems,null,null,null);
    	//设置订单门店对象
    	order.setShop(shopService.findById(shopId));
    	//设置订单顾客对象
    	order.setMember(memberService.findById(customerId));
    	//设置订单流水号
    	order.setSn(sn);
        return order;
    }

}
