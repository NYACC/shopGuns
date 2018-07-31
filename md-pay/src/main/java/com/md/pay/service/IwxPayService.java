package com.md.pay.service;

import java.util.Map;

import com.md.order.model.Order;
import com.md.pay.model.PaymentOrder;

import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.bean.paymch.MchPayRefundNotify;
import weixin.popular.bean.paymch.SecapiPayRefundResult;
import weixin.popular.bean.paymch.UnifiedorderResult;

public interface IwxPayService {
	
	/**
	 * 微信支付下单
	 * @param order
	 * @return
	 */
	UnifiedorderResult wxPayUnifiedorder (Order order ,String ip,String openid);

	String webwxPayNotify(Map<String, String> mapData, MchPayNotify payNotify);
	
	SecapiPayRefundResult wxPayRefund (Order order,PaymentOrder paymentOrder,String money);

	String webwxPayRrfundNotify(Map<String, String> mapData, MchPayRefundNotify payNotify);
	
}
