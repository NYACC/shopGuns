package com.md.cart.warpper;

import java.util.List;
import java.util.Map;

import com.md.cart.factory.CartFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 购物车包装类
 *
 * @authr 
 * @date 2017年2月19日15:07:29
 */
public class CartWarpper extends BaseControllerWarpper {

    public CartWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("cartItems", CartFactory.me().getCatItems((Long)map.get("id")));
    }

}
