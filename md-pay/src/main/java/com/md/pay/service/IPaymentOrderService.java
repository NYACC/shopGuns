package com.md.pay.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.order.constant.OrderStatus;
import com.md.order.model.Order;
import com.md.pay.model.PaymentOrder;

public interface IPaymentOrderService extends IService<PaymentOrder> {
	/**
	 * 根据订单编号,支付单号查找支付记录
	 * 
	 * @return
	 */
	List<Map<String, Object>> find(PaymentOrder paymentOrder);
	
	PaymentOrder selectByOrdersn(String sn);
}
