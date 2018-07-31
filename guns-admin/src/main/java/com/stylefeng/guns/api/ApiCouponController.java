package com.stylefeng.guns.api;

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
import com.md.coupon.model.Coupon;
import com.md.coupon.service.ICouponCodeService;
import com.md.coupon.service.ICouponService;
import com.md.coupon.warpper.CouponWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 优惠券接口
 * @author 54476
 *
 */

@Controller
@RequestMapping("/coupon")
public class ApiCouponController extends BaseController{

	@Resource
	ICouponService couponService;
	
	@Resource
	ICouponCodeService couponCodeService;
	
	
	@ApiOperation(value = "获取门店优惠券列表", notes = "获取门店优惠券列表")
	@RequestMapping(value = "getCouponList",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getCouponList(@ApiParam("门店id") @RequestParam(value = "shopId", required = true)@RequestBody long shopId) {
		JSONObject jb = new JSONObject();
		List<Map<String, Object>> result = couponService.getList(shopId);
		jb.put("data", super.warpObject(new CouponWarpper(result)));
		return jb;
	}
	
	@ApiOperation(value = "获取我的优惠券列表", notes = "获取我的优惠券列表")
	@RequestMapping(value = "getMyCouponList",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getMyCouponList(@ApiParam("用户id") @RequestParam(value = "memberId", required = true)@RequestBody long memberId) {
		JSONObject jb = new JSONObject();
		List<Map<String, Object>> result = couponService.getMyCouponList(memberId);
		jb.put("data", super.warpObject(new CouponWarpper(result)));
		return jb;
	}
	
	@ApiOperation(value = "优惠券详情", notes = "优惠券详情")
	@RequestMapping(value = "getCouponDetail",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getCouponDetail(@ApiParam("优惠券id") @RequestParam(value = "couponId", required = true)@RequestBody long couponId) {
		JSONObject jb = new JSONObject();
		Coupon coupon = couponService.getById(couponId);
		jb.put("data", coupon);
		return jb;
	}
	
	@ApiOperation(value = "领取优惠券", notes = "领取优惠券")
	@RequestMapping(value = "receiveCoupon",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject receiveCoupon(@ApiParam("优惠券id") @RequestParam(value = "couponId", required = true)@RequestBody long couponId,
			@ApiParam("用户id") @RequestParam(value = "memberId", required = true)@RequestBody long memberId) {
		JSONObject jb = new JSONObject();
		Boolean flag = couponCodeService.receive(couponId,memberId);
		jb.put("data", flag);
		return jb;
	}
}
