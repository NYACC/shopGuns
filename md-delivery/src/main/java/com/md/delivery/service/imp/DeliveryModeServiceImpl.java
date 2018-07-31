package com.md.delivery.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.delivery.dao.DeliveryModeMapper;
import com.md.delivery.model.DeliveryMode;
import com.md.delivery.service.IDeliveryModeService;

@Service
public class DeliveryModeServiceImpl extends ServiceImpl<DeliveryModeMapper, DeliveryMode>
		implements IDeliveryModeService {

	@Resource
	DeliveryModeMapper deliveryModeMapper;

	@Override
	public List<Map<String, Object>> list() {
		Wrapper<DeliveryMode> wrapper = new EntityWrapper<>();
		wrapper.orderBy("sequence", true);
		List<Map<String, Object>> deliveryModes = deliveryModeMapper.selectMaps(wrapper);
		return deliveryModes;
	}

	@Override
	public void add(DeliveryMode deliveryMode) {
		deliveryModeMapper.insert(deliveryMode);
	}

	@Override
	public DeliveryMode getById(Long deliveryModeId) {
		return deliveryModeMapper.selectById(deliveryModeId);
	}

	@Override
	public void update(DeliveryMode deliveryMode) {
		deliveryModeMapper.updateById(deliveryMode);
	}

	@Override
	public void deleteById(Long deliveryModeId) {
		deliveryModeMapper.deleteById(deliveryModeId);
	}

}
