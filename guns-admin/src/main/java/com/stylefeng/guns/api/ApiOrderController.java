package com.stylefeng.guns.api;

import java.util.Date;
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
import com.md.goods.model.Product;
import com.md.goods.model.Shop;
import com.md.goods.service.IGoodsService;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IProductService;
import com.md.goods.service.IShopService;
import com.md.goods.service.IUploadFileService;
import com.md.order.model.Evaluation;
import com.md.order.model.Order;
import com.md.order.model.OrderItem;
import com.md.order.service.IEvaluationService;
import com.md.order.service.IOrderItemService;
import com.md.order.service.IOrderService;
import com.md.order.warpper.OrderItemWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.DateUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 订单控制器
 * @author 54476
 *
 */
@Controller
@RequestMapping("/order")
public class ApiOrderController extends BaseController{

	@Resource
	IEvaluationService evaluationService;
	
	@Resource
	IOrderItemService orderItemService;
	
	@Resource
	IOrderService orderService;
	
	@Resource
	IProductService productService;
	
	@Resource
	IShopService shopService;
	
	@Resource
	IGoodsService goodsService;
	
	@Resource
	IPriceTagService priceTagService;
	
	@Resource
	IUploadFileService uploadFileService;
	
	@ApiOperation(value = "添加评价", notes = "添加评价")
	@RequestMapping(value = "/addEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addEvaluation(
			@ApiParam("用户id") @RequestParam(value = "memberId", required = true) @RequestBody Long memberId,
			@ApiParam("订单项id") @RequestParam(value = "orderItemId", required = true) @RequestBody Long orderItemId,
			@ApiParam("评价详情") @RequestParam(value = "detail", required = true) @RequestBody String detail,
			@ApiParam("评价等级") @RequestParam(value = "level", required = true) @RequestBody Integer level) {
		JSONObject jb = new JSONObject();
		OrderItem orderItem = orderItemService.selectById(orderItemId);
		Order order = orderService.getById(orderItem.getOrderId());
		Product product = productService.findById(orderItem.getProductId());
		Evaluation evaluation = new Evaluation();
		evaluation.setCreateTime(DateUtil.format(new Date()));
		evaluation.setDetail(detail);
		evaluation.setLevel(level);
		evaluation.setGoodsId(orderItem.getGoodsId());
		evaluation.setMemberId(memberId);
		evaluation.setOrderItemId(orderItemId);
		evaluation.setShopId(order.getShopId());
		evaluation.setSpecName(product.getName());
		evaluationService.insert(evaluation);
		jb.put("data", "success");
		return jb;
	}
	
	@ApiOperation(value = "获取评价列表", notes = "获取评价列表")
	@RequestMapping(value = "/getEvaluationList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getEvaluationList(
			@ApiParam("商品id") @RequestParam(value = "goodsId", required = true) @RequestBody Long goodsId,
			@ApiParam("门店id") @RequestParam(value = "shopId", required = true) @RequestBody Long shopId,
			@ApiParam("当前页") @RequestParam(value = "index", required = true) @RequestBody Integer index) {
		JSONObject jb = new JSONObject();
		List<Evaluation> list = evaluationService.findListByPage(goodsId, shopId, index);
		jb.put("data", list);
		return jb;
	}
	
	@ApiOperation(value = "获取订单列表", notes = "获取订单列表")
	@RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getOrderList(
			@ApiParam("用户id") @RequestParam(value = "memberId", required = true) @RequestBody Long memberId,
			@ApiParam("订单状态") @RequestParam(value = "status", required = false) @RequestBody Integer status,
			@ApiParam("当前页") @RequestParam(value = "index", required = true) @RequestBody Integer index) {
		JSONObject jb = new JSONObject();
		List<Order> orderResult = orderService.findListByPage(memberId, status, index);
		if(orderResult.size()>0) {
			for(Order order : orderResult) {
				Shop shop = shopService.findById(order.getShopId());
				order.setShop(shop);
				List<Map<String, Object>> itemResult = orderItemService.getListByOrderId(order.getId());
				order.setItemObject(super.warpObject(new OrderItemWarpper(itemResult)));
			}
		}
		jb.put("data", orderResult);
		return jb;
	}
	
	@ApiOperation(value = "修改订单状态", notes = "修改订单状态")
	@RequestMapping(value = "/changeOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject changeOrderStatus(
			@ApiParam("订单id") @RequestParam(value = "orderId", required = true) @RequestBody Long orderId,
			@ApiParam("状态") @RequestParam(value = "status", required = true) @RequestBody Integer status) {
		JSONObject jb = new JSONObject();
		Order order = orderService.getById(orderId);
		//订单状态判断  需要新增
		order.setStatus(status);
		orderService.update(order);
		jb.put("data", "success");
		return jb;
	}
	
	@ApiOperation(value = "获取订单详情", notes = "获取订单详情")
	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getOrderDetail(
			@ApiParam("订单id") @RequestParam(value = "orderId", required = true) @RequestBody Long orderId) {
		JSONObject jb = new JSONObject();
		Order order = orderService.getById(orderId);
		Shop shop = shopService.findById(order.getShopId());
		order.setShop(shop);
		List<Map<String, Object>> itemResult = orderItemService.getListByOrderId(order.getId());
		order.setItemObject(super.warpObject(new OrderItemWarpper(itemResult)));
		jb.put("data", order);
		return jb;
	}
	
	@ApiOperation(value = "批量提交订单", notes = "批量提交订单")
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject submitOrder(
			@ApiParam("订单列表") @RequestParam(value = "orderList", required = true) @RequestBody List<Order> orderList) {
		JSONObject jb = new JSONObject();
		for(Order order : orderList) {
			orderService.add(order);
			for(OrderItem item:order.getOrderItems()) {
				priceTagService.reduceInventory(item.getProductId(), order.getShopId(), item.getQuantity());
				orderItemService.insert(item);
			}
		}
		jb.put("data", "success");
		return jb;
	}
}
