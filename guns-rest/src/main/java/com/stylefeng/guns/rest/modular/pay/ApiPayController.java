package com.stylefeng.guns.rest.modular.pay;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.md.member.model.Member;
import com.md.member.service.IMemberService;
import com.md.order.model.Order;
import com.md.order.service.IOrderService;
import com.md.pay.service.IwxPayService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.ApiException;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.pay.dto.PayRequest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.bean.paymch.MchPayRefundNotify;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;

/**
 * 客户信息
 * 
 * @author 54476
 *
 */
@RestController
@RequestMapping("/pay")
public class ApiPayController extends BaseController {

	@Resource
	IOrderService orderServiceImpl;
	@Resource
	IMemberService MemberServiceImpl;
	@Resource
	IwxPayService wxPayServiceImpl;

	/**
	 * 微信支付
	 */
	@ApiOperation(value = "微信支付", notes = "微信支付")
	@RequestMapping(value = "/wxpay", method = { RequestMethod.POST })
	public ResponseEntity<?> wxpay(@RequestBody PayRequest payRequest,
			HttpServletRequest request) {
		JSONObject jb = new JSONObject();
		Order order = orderServiceImpl.selectById(payRequest.getOrderId());
		Member member = MemberServiceImpl.selectById(payRequest.getMemberId());
		String memberIp = request.getRemoteAddr();
		if (ToolUtil.isEmpty(order)) {
			return ResponseEntity.ok(new ApiException(BizExceptionEnum.ORDER_NULL));
		}
		
		UnifiedorderResult unifiedorderResult =wxPayServiceImpl.wxPayUnifiedorder(order, memberIp, member.getOpenId());
		
		jb.put("code", unifiedorderResult.getReturn_code());
		jb.put("msg", unifiedorderResult.getReturn_msg());
		
		return ResponseEntity.ok(jb);
	}
	
	/**
	 * 微信支付成功通知接口(微信端)
	 * 
	 */
	@RequestMapping(value = "/webwxPayNotify", method =  RequestMethod.POST)
	@ApiOperation(value = "微信支付成功通知接口(微信端)", notes = "")
	public String webwxPayNotify(HttpServletRequest request) {
		// 获取请求数据
		String xmlData = null;
		try {
			xmlData = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 将XML转为MAP,确保所有字段都参与签名验证
		Map<String, String> mapData = XMLConverUtil.convertToMap(xmlData);
		// 转换数据对象
		MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xmlData);		
		payNotify.buildDynamicField(mapData);
		String result = wxPayServiceImpl.webwxPayNotify(mapData,payNotify);
			
		if ("success".equals(result)) {
			return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		} else {
			return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[参数格式校验错误]]></return_msg></xml>";
		}

	}
	/**
	 * 微信退款成功通知接口(微信端)
	 * 
	 */
	@RequestMapping(value = "/webwxRefundNotify", method =  RequestMethod.POST)
	@ApiOperation(value = "微信支付成功通知接口(微信端)", notes = "")
	public String webwxRefundNotify(HttpServletRequest request) {
		// 获取请求数据
		String xmlData = null;
		try {
			xmlData = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 将XML转为MAP,确保所有字段都参与签名验证
		Map<String, String> mapData = XMLConverUtil.convertToMap(xmlData);
		// 转换数据对象
		MchPayRefundNotify payNotify = XMLConverUtil.convertToObject(MchPayRefundNotify.class, xmlData);
		String result = wxPayServiceImpl.webwxPayRrfundNotify(mapData,payNotify);
		
		if ("success".equals(result)) {
			return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		} else {
			return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[参数格式校验错误]]></return_msg></xml>";
		}
		
	}
}
