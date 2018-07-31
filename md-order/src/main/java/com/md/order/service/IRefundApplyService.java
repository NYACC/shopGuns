package com.md.order.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.order.model.Order;
import com.md.order.model.RefundApply;

public interface IRefundApplyService extends IService<RefundApply>{

	boolean add(Order order, String applyWhy);
	
	List<Map<String, Object>> find(RefundApply refundApply);

	
}
