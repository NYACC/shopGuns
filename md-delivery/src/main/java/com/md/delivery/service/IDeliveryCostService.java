package com.md.delivery.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.delivery.model.DeliveryCost;

public interface IDeliveryCostService extends IService<DeliveryCost> {

	/**
	 * 添加
	 * 
	 * @param deliveryCost
	 */
	void add(DeliveryCost deliveryCost);

	/**
	 * 删除配送方式下的所有配送费
	 * 
	 * @param deliveryModeId
	 */
	void deleteByMode(Long deliveryModeId);

	/**
	 * 获得该地区的配送费
	 * 
	 * @param modeId
	 * @param areaIds
	 * @param deliveryArea 
	 * @param isdelivery 
	 * @return
	 */
	List<Map<String, Object>> findCosts(Long modeId, List<Long> areaIds, List<Long> deliveryArea, Boolean isdelivery);

	/**
	 * 修改配送费
	 * 
	 * @param deliveryCost
	 */
	void update(DeliveryCost deliveryCost);

	/**
	 * 通过id查找
	 * 
	 * @param id
	 * @return
	 */
	DeliveryCost getById(Long id);

	/**
	 * 通过地址和配送方式获得配送费
	 * 
	 * @param modeId
	 * @param areaId
	 * @return
	 */
	DeliveryCost getCost(Long modeId, Long areaId, Long deliveryArea);

	/**
	 * 修改通过地区和配送方式
	 * 
	 * @param deliveryCost
	 */
	void updateByModeArea(DeliveryCost deliveryCost);

}
