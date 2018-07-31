package com.md.order.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.order.dao.OrderItemMapper;
import com.md.order.model.OrderItem;
import com.md.order.service.IOrderItemService;

@Service
@Transactional
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {
	@Resource
	OrderItemMapper orderItemMapper;

	@Override
	public void addAll(Long orderId, Set<OrderItem> orderItems) {
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItemMapper.insert(orderItem);
		}
	}

	@Override
	public Set<OrderItem> findByOrderId(Long orderId) {
		HashSet<OrderItem> itemSet = new HashSet<>();
		Wrapper<OrderItem> wrapper = new EntityWrapper<>();
		wrapper.eq("orderId", orderId);
		itemSet.addAll(orderItemMapper.selectList(wrapper));
		return itemSet;
	}

	@Override
	public List<Map<String, Object>> getListByOrderId(Long orderId) {
		Wrapper<OrderItem> wrapper = new EntityWrapper<>();
		wrapper.eq("orderId", orderId);
		return orderItemMapper.selectMaps(wrapper);
	}

}
