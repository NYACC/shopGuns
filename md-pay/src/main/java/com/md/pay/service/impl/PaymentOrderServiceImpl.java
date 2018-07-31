package com.md.pay.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.pay.dao.PaymentOrderMapper;
import com.md.pay.model.PaymentOrder;
import com.md.pay.service.IPaymentOrderService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderMapper, PaymentOrder> implements IPaymentOrderService {

	@Resource
	PaymentOrderMapper paymentOrderMapper;
	
	@Override
	public List<Map<String, Object>> find(PaymentOrder paymentOrder) {
		// TODO 自动生成的方法存根
		Wrapper<PaymentOrder> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(paymentOrder)) {
			if (ToolUtil.isNotEmpty(paymentOrder.getOutTradeNo())) {
				wrapper.eq("outTradeNo", paymentOrder.getOutTradeNo());
			}
			if (ToolUtil.isNotEmpty(paymentOrder.getTransactionId())) {
				wrapper.eq("transactionId", paymentOrder.getTransactionId());
			}
		}
		return paymentOrderMapper.selectMaps(wrapper);
	}

	@Override
	public PaymentOrder selectByOrdersn(String orderSn) {
		// TODO 自动生成的方法存根
		Wrapper<PaymentOrder> wrapper = new EntityWrapper<>();
		wrapper.eq("outTradeNo", orderSn);
		List<PaymentOrder> paymentOrders = paymentOrderMapper.selectList(wrapper);
		if(paymentOrders.size() > 0 ){
			return paymentOrders.get(0);
		}
		return null;
	}

	
}
