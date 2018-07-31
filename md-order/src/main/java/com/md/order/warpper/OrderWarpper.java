package com.md.order.warpper;

import com.md.order.factory.OrderFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.md.order.constant.OrderStatus;

import java.util.List;
import java.util.Map;

/**
 * 订单列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class OrderWarpper extends BaseControllerWarpper {

    public OrderWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", OrderStatus.valueOf((Integer)map.get("status")));
        map.put("shopName", OrderFactory.me().getShopName((Long)map.get("shopId")));
        map.put("customerName",OrderFactory.me().getCustomerName((Long)map.get("memberId")));
    }

}
