package com.md.order.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.order.model.Inventory;

public interface IInventoryService extends IService<Inventory>{
	/**
	 * 查找出入库记录
	 * @param inventory
	 * @param shopIds
	 * @param operatorIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Map<String,Object>> find(Inventory inventory, Long shopId, List<Integer> operatorIds, Timestamp startTime, Timestamp endTime);

	/**
	 * 根据Id查找
	 * @param id
	 * @return
	 */
	Inventory getById(Long id);

	/**
	 * 添加出入库记录
	 * @param inventory
	 */
	void add(Inventory inventory);
	
}
