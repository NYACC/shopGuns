package com.md.order.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.order.model.OrderLog;

public interface IOrderLogService extends IService<OrderLog> {
	/**
	 * 根据订单sn获取订单日志
	 */
	List<Map<String, Object>> findBySn(String sn);
}
