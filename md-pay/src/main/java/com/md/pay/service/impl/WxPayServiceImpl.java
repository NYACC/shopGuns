package com.md.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.md.order.dao.OrderMapper;
import com.md.order.model.Order;
import com.md.pay.dao.PaymentOrderMapper;
import com.md.pay.dao.RefundOrderMapper;
import com.md.pay.model.PaymentOrder;
import com.md.pay.model.RefundOrder;
import com.md.pay.service.IwxPayService;

import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.bean.paymch.MchPayRefundNotify;
import weixin.popular.bean.paymch.SecapiPayRefund;
import weixin.popular.bean.paymch.SecapiPayRefundResult;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.util.SignatureUtil;

@Service
public class WxPayServiceImpl implements IwxPayService {

	@Resource
	OrderMapper orderMapper;
	@Resource
	PaymentOrderMapper paymentOrderMapper;
	@Resource
	RefundOrderMapper refundOrderMapper;
	
	
	// 微信商户号：*****
	private static final String MCHID = "1498707612";
	// 微信支付回调地址
	private static final String NOTIFYURL = "http://www.shop.com/shop/pay/webwxPayNotify";
	// 微信退款回调地址
	private static final String REFUNDNOTIFYURL = "http://www.shop.com/shop/pay/webwxRefundNotify";
	// 微信交易类型
	private static final String TRADETYPE = "JSAPI";
	// 微信APIKEY
	private static final String APIKEY = "qianyouhangkongwuliupingtai20188";

	private static final String APPID = "wxddbe7c1af32f75f0";

	private static final String SECRET = "07e299a95da4cb3acdebc995316284fe";
	
	private static final String GRANT_TYPE = "client_credential";

	private static final String REDIRECT_URI = "http://www.qy-hk.com/api/weixin/redirecturl";
	
	private static final String REDIRECT_URI2 = "http://www.qy-hk.com/api/weixin/redirecturl2";

	private static final String STATE = "123";

	
	@Override
	public UnifiedorderResult wxPayUnifiedorder(Order order ,String ip ,String openid) {
		// TODO 自动生成的方法存根
		Unifiedorder unifiedorder = new Unifiedorder();
		
		unifiedorder.setAppid(APPID);
		unifiedorder.setMch_id(MCHID);
		unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
		unifiedorder.setBody("为美商城");
		unifiedorder.setOut_trade_no(String.valueOf(order.getSn()));
		unifiedorder.setTotal_fee(String.valueOf(order.getActualPay().multiply(new BigDecimal(100))));
		unifiedorder.setSpbill_create_ip(ip);
		unifiedorder.setNotify_url(NOTIFYURL);
		unifiedorder.setTrade_type(TRADETYPE);
		unifiedorder.setOpenid(openid);
		
		return PayMchAPI.payUnifiedorder(unifiedorder, APIKEY);
	}

	@Override
	public String webwxPayNotify(Map<String, String> mapData, MchPayNotify payNotify) {
		// TODO 自动生成的方法存根
		if (SignatureUtil.validateSign(mapData, APIKEY)) {
			String return_code = mapData.get("return_code");
			if (return_code.equalsIgnoreCase("SUCCESS")) {
				String out_trade_no = mapData.get("out_trade_no");
				Order orderInfo = orderMapper.selectById(Long.valueOf(out_trade_no));	
				//记录付款通知
				PaymentOrder paymentOrder = new PaymentOrder(orderInfo, payNotify);
				paymentOrderMapper.insert(paymentOrder);
				return "success";
			} else {
				return "ERROR";
			}
		} else {
			return "ERROR";	
		}
	}

	@Override
	public SecapiPayRefundResult wxPayRefund(Order order,PaymentOrder paymentOrder, String money) {
		// TODO 自动生成的方法存根
		
		SecapiPayRefund secapiPayRefund = new SecapiPayRefund();
		
		secapiPayRefund.setAppid(APPID);
		secapiPayRefund.setMch_id(MCHID);
		secapiPayRefund.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
		secapiPayRefund.setTransaction_id(paymentOrder.getTransactionId());
		secapiPayRefund.setOut_refund_no(String.valueOf(new Date().getTime())+String.valueOf((int)((Math.random()* 9 + 1) * 100000)));
		secapiPayRefund.setTotal_fee(paymentOrder.getTotalFee());
		secapiPayRefund.setRefund_fee(Integer.valueOf(money));
		secapiPayRefund.setNotify_url(REFUNDNOTIFYURL);
		String keystorepath=this.getClass().getResource("/").getPath()+ "/apiclient_cert.p12";
		System.out.println("result======"+keystorepath);
		LocalHttpClient.initMchKeyStore(MCHID, keystorepath);
		SecapiPayRefundResult secapiPayRefundResult = PayMchAPI.secapiPayRefund(secapiPayRefund, APIKEY);
		return secapiPayRefundResult;
	}

	@Override
	public String webwxPayRrfundNotify(Map<String, String> mapData, MchPayRefundNotify payNotify) {
		// TODO 自动生成的方法存根
		if (SignatureUtil.validateSign(mapData, APIKEY)) {
			String return_code = mapData.get("return_code");
			if (return_code.equalsIgnoreCase("SUCCESS")) {
				String out_trade_no = mapData.get("out_trade_no");
				Order orderInfo = orderMapper.selectById(Long.valueOf(out_trade_no));	
				//记录付款通知
				RefundOrder refundOrder = new RefundOrder(orderInfo,payNotify);
				refundOrderMapper.insert(refundOrder);
				return "success";
			} else {
				return "ERROR";
			}
		} else {
			return "ERROR";	
		}
	}

}
