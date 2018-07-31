package com.stylefeng.guns.rest.modular.coupon;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.md.coupon.model.Coupon;
import com.md.coupon.service.ICouponCodeService;
import com.md.coupon.service.ICouponService;
import com.md.coupon.warpper.CouponWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.rest.modular.coupon.dto.CouponRequest;

import io.swagger.annotations.ApiOperation;

/**
 * 优惠券接口
 * @author 54476
 *
 */

@RestController
@RequestMapping("/coupon")
public class ApiCouponController extends BaseController{

	@Resource
	ICouponService couponService;
	
	@Resource
	ICouponCodeService couponCodeService;
	
	
	@ApiOperation(value = "获取门店优惠券列表", notes = "获取门店优惠券列表")
	@RequestMapping(value = "getCouponList",method = RequestMethod.POST)
	public ResponseEntity<?> getCouponList(@RequestBody CouponRequest couponRequest) {
		List<Map<String, Object>> result = couponService.getList(couponRequest.getShopId());
		return ResponseEntity.ok(super.warpObject(new CouponWarpper(result)));
	}
	
	@ApiOperation(value = "获取我的优惠券列表", notes = "获取我的优惠券列表")
	@RequestMapping(value = "getMyCouponList",method = RequestMethod.POST)
	public ResponseEntity<?> getMyCouponList(@RequestBody CouponRequest couponRequest) {
		List<Map<String, Object>> result = couponService.getMyCouponList(couponRequest.getMemberId());
		return ResponseEntity.ok(super.warpObject(new CouponWarpper(result)));
	}
	
	@ApiOperation(value = "优惠券详情", notes = "优惠券详情")
	@RequestMapping(value = "getCouponDetail",method = RequestMethod.POST)
	public ResponseEntity<?> getCouponDetail(@RequestBody CouponRequest couponRequest) {
		Coupon coupon = couponService.getById(couponRequest.getCouponId());
		return ResponseEntity.ok(coupon);
	}
	
	@ApiOperation(value = "领取优惠券", notes = "领取优惠券")
	@RequestMapping(value = "receiveCoupon",method = RequestMethod.POST)
	public ResponseEntity<?> receiveCoupon(@RequestBody CouponRequest couponRequest) {
		Boolean flag = couponCodeService.receive(couponRequest.getCouponId(),couponRequest.getMemberId());
		return ResponseEntity.ok(flag);
	}
}
