package com.md.goods.warpper;

import java.util.Map;

import com.md.goods.factory.CategoryFactory;
import com.md.goods.factory.ParamFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 参数列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class ParamWarpper extends BaseControllerWarpper {

    public ParamWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	 //itemId为Param中的属性
    	 map.put("itemName", ParamFactory.me().getItemsNames((String) map.get("itemId")));
    	 map.put("sortName", CategoryFactory.me().getCategoryName((Long) map.get("categoryId")));
    }

}
