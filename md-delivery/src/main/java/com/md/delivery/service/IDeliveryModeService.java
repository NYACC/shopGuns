package com.md.delivery.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.delivery.model.DeliveryMode;

public interface IDeliveryModeService extends IService<DeliveryMode> {

	/**
	 * 获取配送方式列表
	 * 
	 * @return
	 */
	List<Map<String, Object>> list();

	/**
	 * 添加配送方式
	 * 
	 * @param deliveryMode
	 */
	void add(DeliveryMode deliveryMode);

	/**
	 * 通过id获得配送方式
	 * 
	 * @param deliveryModeId
	 * @return
	 */
	DeliveryMode getById(Long deliveryModeId);

	/**
	 * 修改配送方式
	 * 
	 * @param deliveryMode
	 */
	void update(DeliveryMode deliveryMode);

	/**
	 * 删除
	 * 
	 * @param deliveryModeId
	 */
	void deleteById(Long deliveryModeId);

}
