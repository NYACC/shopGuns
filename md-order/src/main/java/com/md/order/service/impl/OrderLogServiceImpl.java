package com.md.order.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.order.dao.OrderLogMapper;
import com.md.order.model.OrderLog;
import com.md.order.service.IOrderLogService;

@Service
@Transactional
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OrderLog> implements IOrderLogService {

	@Resource
	OrderLogMapper orderLogMapper;

	@Override
	public List<Map<String, Object>> findBySn(String sn) {
		Wrapper<OrderLog> wrapper = new EntityWrapper<>();
		wrapper.eq("sn", sn);
		List<Map<String, Object>> selectMaps = orderLogMapper.selectMaps(wrapper);
		return selectMaps;
	}

}
