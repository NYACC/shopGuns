package com.md.goods.warpper;

import com.md.goods.factory.BrandFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 品牌列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class BrandWarpper extends BaseControllerWarpper {

    public BrandWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", BrandFactory.me().getBrandStatusName((Integer) map.get("status")));
    }

}
