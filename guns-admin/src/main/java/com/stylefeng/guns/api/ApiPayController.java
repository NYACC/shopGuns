package com.stylefeng.guns.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.md.cart.service.ICartService;
import com.md.member.factory.PasswordFactory;
import com.md.member.model.Address;
import com.md.member.model.Balance;
import com.md.member.model.Favorite;
import com.md.member.model.Integral;
import com.md.member.model.Member;
import com.md.member.model.MemberCard;
import com.md.member.service.IAddressService;
import com.md.member.service.IBalanceService;
import com.md.member.service.IFavoriteItemService;
import com.md.member.service.IFavoriteService;
import com.md.member.service.IIntegralService;
import com.md.member.service.IMemberCardService;
import com.md.member.service.IMemberService;
import com.md.member.service.imp.MemberServiceImpl;
import com.md.member.warpper.MemberWarpper;
import com.md.order.model.Order;
import com.md.order.service.IOrderService;
import com.md.pay.service.IwxPayService;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.cache.CacheKit;
import com.stylefeng.guns.core.exception.ApiException;
import com.stylefeng.guns.core.util.ToolUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;

/**
 * 客户信息
 * 
 * @author 54476
 *
 */
@Controller
@RequestMapping("/member")
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
	public @ResponseBody Object wxpay(
			@ApiParam("订单id，必填") @RequestParam(value = "orderId", required = true) Long orderId,
			@ApiParam("用户id，必填") @RequestParam(value = "memberId", required = true) Long memberId,
			HttpServletRequest request) {
		JSONObject jb = new JSONObject();
		Order order = orderServiceImpl.selectById(orderId);
		Member member = MemberServiceImpl.selectById(memberId);
		String memberIp = request.getRemoteAddr();
		if (ToolUtil.isEmpty(order)) {
			return new ApiException(BizExceptionEnum.ORDER_NULL);
		}
		
		UnifiedorderResult unifiedorderResult =wxPayServiceImpl.wxPayUnifiedorder(order, memberIp, member.getOpenId());
		
		jb.put("code", unifiedorderResult.getReturn_code());
		jb.put("msg", unifiedorderResult.getReturn_msg());
		return jb;
		
		
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

}
