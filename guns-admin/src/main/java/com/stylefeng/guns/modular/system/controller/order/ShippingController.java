package com.stylefeng.guns.modular.system.controller.order;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONArray;
import com.md.goods.service.IProductService;
import com.md.goods.service.IShopService;
import com.md.order.constant.InventoryType;
import com.md.order.constant.OrderStatus;
import com.md.order.factory.InventoryFactory;
import com.md.order.factory.ShippingFactory;
import com.md.order.model.Inventory;
import com.md.order.model.Order;
import com.md.order.model.OrderItem;
import com.md.order.model.Shipping;
import com.md.order.model.ShippingItem;
import com.md.order.service.IInventoryService;
import com.md.order.service.IOrderItemService;
import com.md.order.service.IOrderService;
import com.md.order.service.IShippingItemService;
import com.md.order.service.IShippingService;
import com.md.order.warpper.OrderWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 发货单控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/shipping")
public class ShippingController extends BaseController {

	@Resource
    IOrderService orderService;
	@Resource
    IOrderItemService orderItemService;
	@Resource
    IShippingService shippingService;
	@Resource
    IShippingItemService shippingItemService;
	@Resource
    IInventoryService inventoryService;
	@Resource
	IProductService productService;
	@Resource
	IShopService shopService;

    private String PREFIX = "/order/shipping/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }



    /**
     * 获取未发货的订单
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Order order) {
    	order.setStatus(OrderStatus.WAIT_SEND.getCode());
    	order.setShopId(shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId()));
        List<Map<String, Object>> orderList = orderService.find(order);
        return super.warpObject(new OrderWarpper(orderList));
    }
    /**
     * 跳转到发货单详情页面
     */
    @RequestMapping("/toAdd/{orderId}")
    public String toAdd(@PathVariable Long orderId, Model model) {
        //发货单内容包括：发货单号、订单编号、物流公司、物流编号、操作员、发货数量
        Order order=orderService.getById(orderId);
        List<Shipping> shippings=shippingService.findByOrderId(orderId);
        if(ToolUtil.isNotEmpty(shippings)){
            Set<ShippingItem> shippingItems = new HashSet<>();
            for(Shipping shipping:shippings){
                shippingItems.addAll(shippingItemService.findByShippingId(shipping.getId()));
            }
            model.addAttribute("orderItems",JSONArray.toJSON(shippingItems));
        }else{
            Set<OrderItem> orderItems=orderItemService.findByOrderId(orderId);
            model.addAttribute("orderItems", JSONArray.toJSON(orderItems));
        }
        model.addAttribute("order",order);
        model.addAttribute("shippingSn", ShippingFactory.me().getNewSn());
        return PREFIX + "shipping.html";
    }
    /**
     * 添加发货单
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(Shipping shipping, String items) {
        //保存数据到发货单里
        shippingService.add(shipping);
        String htmlUnescape = HtmlUtils.htmlUnescape(items);
        List<ShippingItem> shippingItemList = JSONArray.parseArray(htmlUnescape, ShippingItem.class);
        //如果相等，提交订单状态改成已发货。
        Integer quantity=0;
        Integer shipQuantity=0;
        for(ShippingItem item:shippingItemList){
            quantity+=item.getQuantity();
            shipQuantity+=item.getShipQuantity();
            //保存数据到发货单明细里
            item.setShippingId(shipping.getId());
            shippingItemService.add(item);
            
            //添加出库单
            Inventory inventory = new Inventory();
            inventory.setSn(InventoryFactory.me().getNewSn());
            inventory.setBarcode(productService.findById(item.getProductId()).getBarcode());
            inventory.setCreateTime(new Timestamp(new Date().getTime()));
            inventory.setType(InventoryType.OUTPUT.getCode());
            inventory.setGoodsName(item.getGoodsName());
            inventory.setAmount(item.getQuantity());
            inventory.setRemark("商品发货");
            inventory.setOperatorId(ShiroKit.getUser().getId());
            inventory.setShopId(orderService.getById(shipping.getOrderId()).getShopId());
            inventoryService.add(inventory);
        }
        if(shipQuantity==quantity){
            Order order=new Order();
            order.setId(shipping.getOrderId());
            order.setStatus(OrderStatus.WAIT_GAINS.getCode());
            orderService.update(order);
        }
        
        return SUCCESS;
    }
}
