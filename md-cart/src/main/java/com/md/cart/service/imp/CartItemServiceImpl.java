package com.md.cart.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.cart.dao.CartItemMapper;
import com.md.cart.model.CartItem;
import com.md.cart.service.ICartItemService;

@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements ICartItemService {
	@Resource
	CartItemMapper cartItemMapper;

	@Override
	public void add(CartItem cartItem) {
		cartItemMapper.insert(cartItem);
	}

	@Override
	public void deleteById(Long id) {
		cartItemMapper.deleteById(id);
	}

	@Override
	public void deleteAll(Long cartId) {
		Wrapper<CartItem> wrapper = new EntityWrapper<>();
		wrapper.eq("cartId", cartId);
		cartItemMapper.delete(wrapper);
	}

	@Override
	public List<Map<String, Object>> findByCartId(Long cartId) {
		Wrapper<CartItem> wrapper = new EntityWrapper<>();
		wrapper.eq("cartId", cartId);
		return cartItemMapper.selectMaps(wrapper);
	}

	@Override
	public List<CartItem> findByCartShop(Long cartId, Long shopId) {
		Wrapper<CartItem> wrapper = new EntityWrapper<>();
		wrapper.eq("cartId", cartId);
		wrapper.eq("shopId", shopId);
		return cartItemMapper.selectList(wrapper);
	}

	@Override
	public List<Long> getShopListByCartId(Long cartId) {

		return this.baseMapper.getShopListByCartId(cartId);
	}

	@Override
	public CartItem findByTagId(Long tagId, Long cartId) {
		CartItem item = new CartItem();
		item.setCartId(cartId);
		item.setPriceTagId(tagId);
		return cartItemMapper.selectOne(item);
	}
}
