package com.md.cart.service;

import com.baomidou.mybatisplus.service.IService;
import com.md.cart.model.Cart;
import com.md.member.model.Member;

public interface ICartService extends IService<Cart> {
	/**
	 * 通过id查找购物车
	 * 
	 * @param memberId
	 * @return
	 */
	Cart findById(Long memberId);

	/**
	 * 初始化一辆购物车
	 * 
	 * @param member
	 * @return
	 */
	Cart init(Member member);
	
	/**
	 * 新增 /修改购物车
	 * @param memberId
	 * @return
	 */
	Cart addSave(Long memberId);

}
