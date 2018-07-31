package com.md.order.service;

import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.md.order.model.ShippingItem;

public interface IShippingItemService extends IService<ShippingItem> {

	/**
	 * 根据订单编号查找发货单
	 */
	Set<ShippingItem> findByShippingId(Long shipping);

	/**
	 * 添加发货单明细
	 * 
	 * @param shippingItem
	 */
	void add(ShippingItem shippingItem);
}
