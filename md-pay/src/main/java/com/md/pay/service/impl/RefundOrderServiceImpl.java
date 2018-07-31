package com.md.pay.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.pay.dao.RefundOrderMapper;
import com.md.pay.model.RefundOrder;
import com.md.pay.service.IRefundOrderService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class RefundOrderServiceImpl extends ServiceImpl<RefundOrderMapper, RefundOrder> implements IRefundOrderService {

	@Resource
	RefundOrderMapper refundOrderMapper;
	
	@Override
	public List<Map<String, Object>> find(RefundOrder refundOrder) {
		// TODO 自动生成的方法存根
		Wrapper<RefundOrder> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(refundOrder)) {
			if (ToolUtil.isNotEmpty(refundOrder.getOutTradeNo())) {
				wrapper.eq("outTradeNo", refundOrder.getOutTradeNo());
			}
			if (ToolUtil.isNotEmpty(refundOrder.getTransactionId())) {
				wrapper.eq("transactionId", refundOrder.getTransactionId());
			}
		}
		return refundOrderMapper.selectMaps(wrapper);
	}

	
}
