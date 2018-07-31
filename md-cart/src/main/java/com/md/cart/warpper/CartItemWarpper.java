package com.md.cart.warpper;

import com.md.cart.factory.CartItemFactory;
import com.md.goods.model.PriceTag;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 购物车项包装类
 *
 * @authr 
 * @date 2017年2月19日15:07:29
 */
public class CartItemWarpper extends BaseControllerWarpper {

    public CartItemWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("priceTag", CartItemFactory.me().getPriceTag((Long)map.get("priceTagId")));
    	map.put("product", CartItemFactory.me().getProduct((Long)map.get("productId")));
    	map.put("shop", CartItemFactory.me().getShop((Long)map.get("shopId")));
    	map.put("isValid", ((PriceTag)map.get("priceTag")).isValid());
    }

}
