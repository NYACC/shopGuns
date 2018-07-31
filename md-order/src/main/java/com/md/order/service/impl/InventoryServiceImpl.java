package com.md.order.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.order.dao.InventoryMapper;
import com.md.order.model.Inventory;
import com.md.order.service.IInventoryService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
@Transactional
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements IInventoryService {

	@Resource
	InventoryMapper inventoryMapper;

	@Override
	public List<Map<String, Object>> find(Inventory inventory, Long shopId, List<Integer> operatorIds,
			Timestamp startTime, Timestamp endTime) {
		Wrapper<Inventory> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(inventory)) {
			if (ToolUtil.isNotEmpty(inventory.getType())) {
				wrapper.eq("type", inventory.getType());
			}
			if (ToolUtil.isNotEmpty(inventory.getGoodsName())) {
				wrapper.like("goodsName", "%" + inventory.getGoodsName() + "%");
			}
			if (ToolUtil.isNotEmpty(inventory.getBarcode())) {
				wrapper.eq("barcode", inventory.getBarcode());
			}
		}
		if (ToolUtil.isNotEmpty(shopId)) {
			wrapper.eq("shopId", shopId);
		}
		if (ToolUtil.isNotEmpty(operatorIds)) {
			wrapper.in("operatorId", operatorIds);
		}
		if (ToolUtil.isNotEmpty(startTime) && ToolUtil.isNotEmpty(endTime)) {
			wrapper.between("createTime", startTime, endTime);
		}
		wrapper.orderBy("createTime", false);
		List<Map<String, Object>> selectMaps = inventoryMapper.selectMaps(wrapper);
		return selectMaps;
	}

	@Override
	public Inventory getById(Long id) {
		return inventoryMapper.selectById(id);
	}

	@Override
	public void add(Inventory inventory) {
		inventoryMapper.insert(inventory);
	}

}
