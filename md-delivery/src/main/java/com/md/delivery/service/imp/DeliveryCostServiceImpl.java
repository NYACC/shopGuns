package com.md.delivery.service.imp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.delivery.dao.DeliveryCostMapper;
import com.md.delivery.model.DeliveryCost;
import com.md.delivery.service.IDeliveryCostService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class DeliveryCostServiceImpl extends ServiceImpl<DeliveryCostMapper, DeliveryCost>
		implements IDeliveryCostService {

	@Resource
	DeliveryCostMapper deliveryCostMapper;

	@Override
	public void add(DeliveryCost deliveryCost) {
		deliveryCostMapper.insert(deliveryCost);
	}

	@Override
	public void deleteByMode(Long deliveryModeId) {
		Wrapper<DeliveryCost> wrapper = new EntityWrapper<>();
		wrapper.eq("modeId", deliveryModeId);
		deliveryCostMapper.delete(wrapper);
	}

	@Override
	public List<Map<String, Object>> findCosts(Long modeId, List<Long> areaIds, List<Long> deliveryArea,
			Boolean isdelivery) {
		return deliveryCostMapper.findList(modeId, areaIds, deliveryArea, isdelivery);
	}

	@Override
	public void update(DeliveryCost deliveryCost) {
		deliveryCost.setModifyTime(new Timestamp(new Date().getTime()));
		deliveryCostMapper.updateById(deliveryCost);
	}

	@Override
	public DeliveryCost getById(Long id) {
		return deliveryCostMapper.selectById(id);
	}

	@Override
	public DeliveryCost getCost(Long modeId, Long areaId, Long deliveryArea) {
		Wrapper<DeliveryCost> wrapper = new EntityWrapper<>();
		wrapper.eq("modeId", modeId);
		wrapper.eq("areaId", areaId);
		wrapper.eq("deliveryArea", deliveryArea);
		List<DeliveryCost> selectList = deliveryCostMapper.selectList(wrapper);
		if (selectList.size() > 0) {
			return selectList.get(0);
		}
		return null;
	}

	@Override
	public void updateByModeArea(DeliveryCost deliveryCost) {
		if (!ToolUtil.isAllEmpty(deliveryCost.getDeliveryArea(),deliveryCost.getAreaId())) {
			return ;
		}
		DeliveryCost cost = this.getCost(deliveryCost.getModeId(), deliveryCost.getAreaId(),
				deliveryCost.getDeliveryArea());
		if (ToolUtil.isNotEmpty(cost)) {
			deliveryCostMapper.insert(deliveryCost);
		}else{
			deliveryCost.setId(cost.getId());
			this.update(deliveryCost);
		}
	}

}
