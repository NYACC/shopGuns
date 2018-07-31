package com.md.order.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.md.order.model.OrderItem;

public interface IOrderItemService extends IService<OrderItem> {
	/**
	 * 添加订单项
	 */
	void addAll(Long orderId, Set<OrderItem> orderItems);

	/**
	 * 根据订单编号查找订单项
	 */
	Set<OrderItem> findByOrderId(Long orderId);

	/**
	 * 根据订单id获取订单项列表
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getListByOrderId(Long orderId);
}
