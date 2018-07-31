package com.stylefeng.guns.rest.modular.category;

import java.util.List;
import java.util.Map;

import com.md.goods.factory.GoodsFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

public class CategorysWarpper extends BaseControllerWarpper{
	
    public CategorysWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("goodsList", GoodsFactory.me().goodsList(null ,(Long)map.get("id") ,null,null));
    }

}
