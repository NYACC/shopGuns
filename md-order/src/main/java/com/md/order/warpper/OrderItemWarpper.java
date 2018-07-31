package com.md.order.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.md.goods.factory.GoodsFactory;
import com.md.goods.factory.PriceTagFactory;
import com.md.goods.factory.UploadFactory;

import java.util.List;
import java.util.Map;

/**
 * 订单子项列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class OrderItemWarpper extends BaseControllerWarpper {

    public OrderItemWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("goodsName", GoodsFactory.me().getNameById((Long)map.get("goodsId")));
        map.put("productName", PriceTagFactory.me().getProductName((Long)map.get("productId")));
        map.put("imageUrl", UploadFactory.me().getUploadFileUrl(PriceTagFactory.me().getProductImage((Long)map.get("productId"))));
        
    }

}
