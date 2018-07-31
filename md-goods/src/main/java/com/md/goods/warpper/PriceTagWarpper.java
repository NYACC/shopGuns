package com.md.goods.warpper;

import com.md.goods.factory.PriceTagFactory;
import com.md.goods.model.PriceTag;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 价格标签列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class PriceTagWarpper extends BaseControllerWarpper {

    public PriceTagWarpper(List<Map<String, Object>> list) {
        super(list);
    }
    public PriceTagWarpper(PriceTag priceTag) {
        super(priceTag);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("id",map.get("id").toString());
        map.put("goodsName",PriceTagFactory.me().getGoodsName((Long)map.get("goodsId")));
        map.put("productName",PriceTagFactory.me().getProductName((Long)map.get("productId")));
        map.put("productId",map.get("productId").toString());
        map.put("goodsId",map.get("goodsId").toString());
        map.put("storesName", PriceTagFactory.me().getStoresName((Long) map.get("shopId")));
        map.put("shopId",map.get("shopId").toString());
    }

}
