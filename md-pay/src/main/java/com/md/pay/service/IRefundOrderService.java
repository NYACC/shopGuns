package com.md.pay.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.pay.model.RefundOrder;

public interface IRefundOrderService extends IService<RefundOrder> {
	/**
	 * 根据订单编号,支付单号查找支付记录
	 * 
	 * @return
	 */
	List<Map<String, Object>> find(RefundOrder refundOrder);
}
