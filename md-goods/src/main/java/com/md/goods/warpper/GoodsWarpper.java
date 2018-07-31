package com.md.goods.warpper;

import com.md.goods.factory.BrandFactory;
import com.md.goods.factory.CategoryFactory;
import com.md.goods.factory.GoodsFactory;
import com.md.goods.factory.UploadFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 商品列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class GoodsWarpper extends BaseControllerWarpper {

    public GoodsWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("brandName", BrandFactory.me().getBrandName((Long) map.get("brandId")));
        map.put("categoryName", CategoryFactory.me().getMultiplyName(GoodsFactory.me().getCategoryIds((Long) map.get("id"))));
        map.put("imageUrl", UploadFactory.me().getAllUploadFileUrl(map.get("images").toString()));
    }
}
