package com.md.pay.warpper;

import java.util.List;
import java.util.Map;

import com.md.member.factory.MemberFactory;
import com.md.pay.factory.RefundFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 商品列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class RefundOrderWarpper extends BaseControllerWarpper {

    public RefundOrderWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("totalFee", RefundFactory.me().fenConvertYuan(Integer.valueOf(String.valueOf(map.get("totalFee")))));
    	map.put("settlementTotalFee", RefundFactory.me().fenConvertYuan(Integer.valueOf(String.valueOf(map.get("settlementTotalFee")))));
    	map.put("refundFee", RefundFactory.me().fenConvertYuan(Integer.valueOf(String.valueOf(map.get("refundFee")))));
    	map.put("settlementRefundFee", RefundFactory.me().fenConvertYuan(Integer.valueOf(String.valueOf(map.get("settlementRefundFee")))));
    }
}
