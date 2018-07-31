package com.md.pay.warpper;

import java.util.List;
import java.util.Map;

import com.md.member.factory.MemberFactory;
import com.md.pay.factory.RefundFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 支付订单的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class PaymentOrderWarpper extends BaseControllerWarpper {

	public PaymentOrderWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		map.put("memberPhone", MemberFactory.me().getPhoneNumByOpenId(String.valueOf(map.get("openId"))));
		map.put("totalFee", RefundFactory.me().fenConvertYuan(Integer.valueOf(String.valueOf(map.get("totalFee")))));
		map.put("settlementTotalFee",
				RefundFactory.me().fenConvertYuan(Integer.valueOf(String.valueOf(map.get("settlementTotalFee")))));
	}
}
