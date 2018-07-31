package com.md.order.warpper;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 订单列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class OrderLogWarpper extends BaseControllerWarpper {

    public OrderLogWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	//map.put("userName", ConstantFactory.me().getUserNameById((Integer)map.get("userid")));
    }

}
