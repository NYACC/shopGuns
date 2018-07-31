package com.md.order.warpper;

import java.util.List;
import java.util.Map;

import com.md.order.constant.InventoryType;
import com.md.order.factory.OrderFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 出入库单列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class InventoryWarpper extends BaseControllerWarpper {

    public InventoryWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("type", InventoryType.valueOf((Integer)map.get("type")));
        //map.put("operatorName", ConstantFactory.me().getUserNameById((Integer)map.get("operatorId")));
        map.put("shopName", OrderFactory.me().getShopName((Long)map.get("shopId")));
    }

}
