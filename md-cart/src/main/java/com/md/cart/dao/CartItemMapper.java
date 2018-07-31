package com.md.cart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.md.cart.model.CartItem;

public interface CartItemMapper extends BaseMapper<CartItem>{

	List<Long> getShopListByCartId(@Param("cartId")Long cartId);

}
