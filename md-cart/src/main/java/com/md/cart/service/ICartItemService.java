package com.md.cart.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.cart.model.CartItem;

public interface ICartItemService extends IService<CartItem> {
	/**
	 * 添加购物车清单项
	 * 
	 * @param cartItem
	 */
	void add(CartItem cartItem);

	/**
	 * 单项删除购物车清单项
	 * 
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 清空购物车的清单项
	 * 
	 * @param cartId
	 */
	void deleteAll(Long cartId);

	/**
	 * 根据购物车id查找对应的购物车项
	 * 
	 * @param cartId
	 * @return
	 */
	List<Map<String, Object>> findByCartId(Long cartId);
	
	/**
	 * 通过门店id获取 购物车项列表
	 * @param cartId 购物车id
	 * @param shopId 门店id
	 * @return
	 */
	List<CartItem> findByCartShop(Long cartId,Long shopId);
	
	/**
	 * 通过购物车id获取店铺列表
	 * @param cartId
	 * @return
	 */
	List<Long> getShopListByCartId(Long cartId);
	
	/**
	 * 获取相同购物车项
	 * @param tagId  价格标签
	 * @param cartId 购物车
	 * @return
	 */
	CartItem findByTagId(Long tagId,Long cartId);

}
