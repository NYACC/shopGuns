package com.stylefeng.guns.modular.system.controller.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.Service;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IShopService;
import com.md.member.model.Member;
import com.md.member.service.IMemberService;
import com.md.order.constant.OrderStatus;
import com.md.order.factory.OrderFactory;
import com.md.order.model.Order;
import com.md.order.model.OrderItem;
import com.md.order.model.Shipping;
import com.md.order.service.IOrderItemService;
import com.md.order.service.IOrderLogService;
import com.md.order.service.IOrderService;
import com.md.order.service.IShippingItemService;
import com.md.order.service.IShippingService;
import com.md.order.warpper.OrderLogWarpper;
import com.md.order.warpper.OrderWarpper;
import com.stylefeng.guns.common.annotion.OrderLog;
import com.stylefeng.guns.common.constant.dictmap.order.OrderDict;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;



/**
 * 订单控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Resource
    IOrderService orderService;
	@Resource
    IOrderItemService orderItemService;
	@Resource
	IShopService shopService;
	@Resource
	IMemberService memberService;
	@Resource
    IOrderLogService orderLogService;
	@Resource
    IShippingService shippingService;
	@Resource
    IShippingItemService shippingItemService;
	@Resource
	IPriceTagService priceTagService;


    private String PREFIX = "/order/order/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 跳转到订单添加页面
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
    	model.addAttribute("sn", OrderFactory.me().getNewSn());
        return PREFIX + "add.html";
    }

    /**
     * 跳转到订单修改页面
     */
    @RequestMapping("/toEdit/{orderId}")
    public String toEdit(@PathVariable Long orderId, Model model) {
    	Order order = orderService.getById(orderId);
    	order.setOrderItems(orderItemService.findByOrderId(orderId));
    	order.setShop(shopService.findById(order.getShopId()));
    	order.setMember(memberService.findById(order.getMemberId()));
    	model.addAttribute("order",order);
    	model.addAttribute("status", OrderStatus.valueOf(order.getStatus()));
    	model.addAttribute("orderItems",JSONArray.toJSON(order.getOrderItems()));
    	LogObjectHolder.me().set(order);
        return PREFIX + "edit.html";
    }

    /**
     * 跳转到订单详情页面
     */
    @RequestMapping("/detail/{orderId}")
    public String detail(@PathVariable Long orderId, Model model) {
        Order order = orderService.getById(orderId);
        order.setOrderItems(orderItemService.findByOrderId(orderId));
        order.setShop(shopService.findById(order.getShopId()));
        order.setMember(memberService.findById(order.getMemberId()));
        model.addAttribute("order",order);
        model.addAttribute("status",OrderStatus.valueOf(order.getStatus()));
        model.addAttribute("orderItems",JSONArray.toJSON(order.getOrderItems()));
        //封装物流对象
        //调用shippingService里的findByOrderId方法,将结果封装到model里
        List<Shipping> shippings=shippingService.findByOrderId(orderId);
        shippings.stream().forEach(s->{
            s.setShippingItems(shippingItemService.findByShippingId(s.getId()));
        });
        model.addAttribute("shippings",shippings);
        return PREFIX + "order_detail.html";
    }
    /**
     * 获取订单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String customerName,String sn,Integer status) {
    	Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
        Member customer=new Member();
        customer.setName(customerName);
        List<Map<String,Object>> customers=memberService.find(customer);
        List<Map<String,Object>> orderList=new ArrayList<>();
        customers.stream().forEach(c->{
            Order order=new Order();
            order.setSn(sn);
            order.setStatus(status);
            order.setShopId(shopId);
            order.setMemberId((Long)c.get("id"));
            orderList.addAll(orderService.find(order));
        });
        return super.warpObject(new OrderWarpper(orderList));
    }
    
    /**
     * 获取订单日志
     */
    @RequestMapping(value = "/orderLogs/{sn}")
    @ResponseBody
    public Object orderLogs(@PathVariable String sn) {
    	return new OrderLogWarpper(orderLogService.findBySn(sn)).warp();
    }

    /**
     * 修改订单
     */
    @OrderLog(value = "修改订单", key = "sn", dict = OrderDict.class)
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object edit(Order order) {
    	if(ToolUtil.isEmpty(order.getId())){
    		throw new GunsException(BizExceptionEnum.REQUEST_NULL); 
    	}
        orderService.update(order);
        return SUCCESS_TIP;
    }
    
    /**
     * 添加订单
     */
    @OrderLog(value = "添加订单", key = "sn", dict = OrderDict.class)
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String orderJson) {
    	String htmlUnescape = HtmlUtils.htmlUnescape(orderJson);
    	JSONObject parseObject = JSONObject.parseObject(htmlUnescape);
    	Order order = JSONObject.toJavaObject(parseObject, Order.class);
    	//保存订单
    	order.setId(orderService.add(order));
    	//保存订单项
    	orderItemService.addAll(order.getId(), order.getOrderItems());
    	//减少库存
    	for (OrderItem orderItem : order.getOrderItems()) {
			priceTagService.reduceInventory(orderItem.getProductId(), order.getShopId(), orderItem.getQuantity());
		}
        return SUCCESS;
    }
}
