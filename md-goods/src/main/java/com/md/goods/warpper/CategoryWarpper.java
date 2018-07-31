package com.md.goods.warpper;

import java.util.List;
import java.util.Map;

import com.md.goods.factory.CategoryFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 分类列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class CategoryWarpper extends BaseControllerWarpper {

    public CategoryWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", CategoryFactory.me().getCategoryState((Integer)map.get("status")));
    }

}
