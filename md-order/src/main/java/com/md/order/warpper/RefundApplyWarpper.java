package com.md.order.warpper;

import com.md.order.factory.OrderFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.md.member.factory.MemberFactory;
import com.md.order.constant.OrderStatus;

import java.util.List;
import java.util.Map;

/**
 * 退款申请列表
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class RefundApplyWarpper extends BaseControllerWarpper {

    public RefundApplyWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("orderSn", OrderFactory.me().getOrderSn(Long.valueOf(String.valueOf(map.get("orderId")))));
    	map.put("memberPhone", MemberFactory.me().getPhoneNum(Long.valueOf(String.valueOf(map.get("memberId")))));
    	map.put("statusName", OrderStatus.valueOf((Integer)map.get("status")));
    }

}
