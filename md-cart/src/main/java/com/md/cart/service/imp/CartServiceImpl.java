package com.md.cart.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.cart.dao.CartMapper;
import com.md.cart.model.Cart;
import com.md.cart.service.ICartService;
import com.md.member.model.Member;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {
	@Resource
	CartMapper cartMapper;

	@Override
	public Cart findById(Long memberId) {
		Cart cart = new Cart();
		cart.setMemberId(memberId);
		return cartMapper.selectOne(cart);
	}

	@Override
	public Cart init(Member member) {
		Cart cart = new Cart();
		cart.setMemberId(member.getId());
		cartMapper.insert(cart);
		return cart;
	}

	@Override
	public Cart addSave(Long memberId) {
		Cart cart = new Cart();
		cart.setMemberId(memberId);
		Cart cartt = cartMapper.selectOne(cart);
		if (cartt != null) {
			return cartt;
		} else {
			cart.setQuantity(0);
			cartMapper.insert(cart);
			return cart;
		}
	}

}
