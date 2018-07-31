package com.md.order.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.md.order.model.Shipping;

public interface IShippingService extends IService<Shipping> {

	/**
	 * 根据订单编号查找发货单
	 */
	List<Shipping> findByOrderId(Long orderId);

	/**
	 * 添加发货单
	 * 
	 * @param shipping
	 */
	void add(Shipping shipping);
}
