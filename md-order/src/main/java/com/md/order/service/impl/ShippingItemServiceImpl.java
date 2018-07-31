package com.md.order.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.order.dao.ShippingItemMapper;
import com.md.order.model.ShippingItem;
import com.md.order.service.IShippingItemService;

@Service
@Transactional
public class ShippingItemServiceImpl extends ServiceImpl<ShippingItemMapper, ShippingItem>
		implements IShippingItemService {
	@Resource
	ShippingItemMapper shippingItemMapper;

	@Override
	public Set<ShippingItem> findByShippingId(Long ShippingId) {
		Set<ShippingItem> shippingItems = new HashSet<>();
		Wrapper<ShippingItem> wrapper = new EntityWrapper<>();
		wrapper.eq("ShippingId", ShippingId);
		shippingItems.addAll(shippingItemMapper.selectList(wrapper));
		return shippingItems;
	}

	@Override
	public void add(ShippingItem shippingItem) {
		shippingItemMapper.insert(shippingItem);
	}
}
