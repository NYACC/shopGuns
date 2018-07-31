package com.md.order.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.order.constant.OrderStatus;
import com.md.order.model.Order;

public interface IOrderService extends IService<Order> {
	/**
	 * 根据订单编号查找订单
	 * 
	 * @param order
	 * @return
	 */
	List<Map<String, Object>> find(Order order);

	/**
	 * 新增订单
	 */
	Long add(Order order);

	/**
	 * 修改订单
	 */
	void update(Order order);

	/**
	 * 通过id查找订单
	 */
	Order getById(Long id);

	/**
	 * 修改订单的备注
	 * 
	 * @param orderId
	 * @param remark
	 */
	void editRemark(Long orderId, String remark);

	/**
	 * 修改订单的状态
	 * 
	 * @param orderId
	 * @param status
	 */
	void editStatus(Long orderId, OrderStatus status);

	List<Order> findListByPage(Long memberId, Integer status, Integer index);
}
