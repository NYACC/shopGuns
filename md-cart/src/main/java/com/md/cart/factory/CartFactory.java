package com.md.cart.factory;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.md.cart.dao.CartItemMapper;
import com.md.cart.model.CartItem;
import com.md.cart.warpper.CartItemWarpper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 购物车创建工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class CartFactory {
	private CartItemMapper cartItemMapper=SpringContextHolder.getBean(CartItemMapper.class);
	
    public static CartFactory me() {
        return SpringContextHolder.getBean(CartFactory.class);
    }

	public Object  getCatItems(Long cartId) {
		Wrapper<CartItem> wrapper= new EntityWrapper<>();
		wrapper.eq("cartId", cartId);
		List<Map<String, Object>> selectMaps = cartItemMapper.selectMaps(wrapper);
		return new CartItemWarpper(selectMaps).warp();
	}

}
