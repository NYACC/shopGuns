package com.stylefeng.guns.rest.modular.order;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.goods.model.PriceTag;
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
import com.md.order.model.RefundApply;
import com.md.order.service.IEvaluationService;
import com.md.order.service.IOrderItemService;
import com.md.order.service.IOrderService;
import com.md.order.service.IRefundApplyService;
import com.md.order.warpper.OrderItemWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.modular.order.dto.OrderRequest;

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
	
	@Resource
	IRefundApplyService refundApplyService;
	
	@ApiOperation(value = "添加评价", notes = "添加评价")
	@RequestMapping(value = "/addEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addEvaluation(@RequestBody OrderRequest orderRequest) {
		JSONObject jb = new JSONObject();
		OrderItem orderItem = orderItemService.selectById(orderRequest.getOrderItemId());
		Order order = orderService.getById(orderItem.getOrderId());
		Product product = productService.findById(orderItem.getProductId());
		Evaluation evaluation = new Evaluation();
		evaluation.setCreateTime(DateUtil.format(new Date()));
		evaluation.setDetail(orderRequest.getDetail());
		evaluation.setLevel(orderRequest.getLevel());
		evaluation.setGoodsId(orderItem.getGoodsId());
		evaluation.setMemberId(orderRequest.getMemberId());
		evaluation.setOrderItemId(orderRequest.getOrderItemId());
		evaluation.setShopId(order.getShopId());
		evaluation.setSpecName(product.getName());
		evaluationService.insert(evaluation);
		jb.put("data", "success");
		return ResponseEntity.ok(jb);
	}
	
	@ApiOperation(value = "获取评价列表", notes = "获取评价列表")
	@RequestMapping(value = "/getEvaluationList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getEvaluationList(@RequestBody OrderRequest orderRequest) {
		JSONObject jb = new JSONObject();
		List<Evaluation> list = evaluationService.findListByPage(orderRequest.getGoodsId(),orderRequest.getShopId(),orderRequest.getIndex());
		jb.put("data", list);
		return ResponseEntity.ok(jb);
	}
	
	@ApiOperation(value = "获取订单列表", notes = "获取订单列表")
	@RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getOrderList(@RequestBody OrderRequest orderRequest) {
		JSONObject jb = new JSONObject();
		List<Order> orderResult = orderService.findListByPage(orderRequest.getMemberId(), orderRequest.getStatus(), orderRequest.getIndex());
		if(orderResult.size()>0) {
			for(Order order : orderResult) {
				Shop shop = shopService.findById(order.getShopId());
				order.setShop(shop);
				List<Map<String, Object>> itemResult = orderItemService.getListByOrderId(order.getId());
				order.setItemObject(super.warpObject(new OrderItemWarpper(itemResult)));
			}
		}
		jb.put("data", orderResult);
		return ResponseEntity.ok(jb);
	}
	
	@ApiOperation(value = "修改订单状态", notes = "修改订单状态")
	@RequestMapping(value = "/changeOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> changeOrderStatus(@RequestBody OrderRequest orderRequest) {
		JSONObject jb = new JSONObject();
		Order order = orderService.getById(orderRequest.getOrderId());
		//订单状态判断  需要新增
		order.setStatus(orderRequest.getStatus());
		orderService.update(order);
		jb.put("data", "success");
		return ResponseEntity.ok(jb);
	}
	
	@ApiOperation(value = "获取订单详情", notes = "获取订单详情")
	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getOrderDetail(@RequestBody OrderRequest orderRequest) {
		JSONObject jb = new JSONObject();
		Order order = orderService.getById(orderRequest.getOrderId());
		Shop shop = shopService.findById(order.getShopId());
		order.setShop(shop);
		List<Map<String, Object>> itemResult = orderItemService.getListByOrderId(order.getId());
		order.setItemObject(super.warpObject(new OrderItemWarpper(itemResult)));
		jb.put("data", order);
		return ResponseEntity.ok(jb);
	}
	
	@ApiOperation(value = "批量提交订单", notes = "批量提交订单")
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public ResponseEntity<?> submitOrder(@RequestBody List<Order> orderList) {
		JSONObject jb = new JSONObject();
		for(Order order : orderList) {
			orderService.add(order);
			for(OrderItem item:order.getOrderItems()) {
				priceTagService.reduceInventory(item.getProductId(), order.getShopId(), item.getQuantity());
				orderItemService.insert(item);
			}
		}
		jb.put("data", "success");
		return ResponseEntity.ok(jb);
	}
	
	@ApiOperation(value = "申请退款", notes = "申请退款")
	@RequestMapping(value = "/refundApply", method = RequestMethod.POST)
	public ResponseEntity<?> refundApply(@RequestBody OrderRequest orderRequest) {
		JSONObject jb = new JSONObject();
		Order order = orderService.selectById(orderRequest.getApplyWhy());
		Calendar cld = Calendar.getInstance();
		cld.setTime(order.getCreateTime());
		cld.add(Calendar.DATE, 180);
		Timestamp timestamp = DateUtil.format(cld.getTime());
		if(timestamp.before(new Date())){
			jb.put("data", "订单过期,无法退款");
			return ResponseEntity.ok(jb);
		}
		boolean flag;
		if(ToolUtil.isNotEmpty(order)){
			flag = refundApplyService.add(order,orderRequest.getApplyWhy());
		}else{
			jb.put("data", "查找不到订单");
			return ResponseEntity.ok(jb);
		}
		if(flag){
			jb.put("data", "申请成功");
			return ResponseEntity.ok(jb);
		}
		jb.put("data", "请勿重复申请，请联系客服");
		return ResponseEntity.ok(jb);
	}
	
}
