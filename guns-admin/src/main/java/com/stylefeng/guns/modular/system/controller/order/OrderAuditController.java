package com.stylefeng.guns.modular.system.controller.order;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.md.goods.service.IShopService;
import com.md.member.service.IMemberService;
import com.md.order.constant.OrderStatus;
import com.md.order.model.Order;
import com.md.order.service.IOrderItemService;
import com.md.order.service.IOrderService;
import com.md.order.warpper.OrderWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;

/**
 * 订单审核控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/orderAudit")
public class OrderAuditController extends BaseController {

	@Resource
    IOrderService orderService;
	@Resource
    IOrderItemService orderItemService;
	@Resource
	IShopService shopService;
	@Resource
	IMemberService memberService;

    private String PREFIX = "/order/orderAudit/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }
    
    /**
     * 获取待审核订单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
    	Order order = new Order();
    	order.setStatus(OrderStatus.UNCHECK.getCode());
    	order.setShopId(shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId()));
        List<Map<String, Object>> orderList = orderService.find(order);
        return super.warpObject(new OrderWarpper(orderList));
    }
    
    /**
     * 跳转到订单详情页面
     */
    @RequestMapping("/audit/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model) {
        Order order = orderService.getById(orderId);
        order.setOrderItems(orderItemService.findByOrderId(orderId));
        order.setShop(shopService.findById(order.getShopId()));
        order.setMember(memberService.findById(order.getMemberId()));
        model.addAttribute("order",order);
        model.addAttribute("status",OrderStatus.valueOf(order.getStatus()));
        model.addAttribute("orderItems",JSONArray.toJSON(order.getOrderItems()));
        return PREFIX + "audit.html";
    }

    /**
     * 审核通过
     */
    @RequestMapping("/auditPass")
    @ResponseBody
    public Object auditPass(Long orderId,String remark) {
        //修改订单的状态为“待发货”
    	orderService.editStatus(orderId,OrderStatus.WAIT_SEND);
    	//添加备注
    	orderService.editRemark(orderId,remark);
    	return SUCCESS_TIP;
    }
    
    /**
     * 审核不通过
     */
    @RequestMapping("/auditUnPass")
    @ResponseBody
    public Object auditUnPass(Long orderId,String remark) {
        //修改订单的状态为“待发货”
    	orderService.editStatus(orderId,OrderStatus.TRADE_CLOSE);
    	//添加备注
    	orderService.editRemark(orderId,remark);
    	return SUCCESS_TIP;
    }
}
