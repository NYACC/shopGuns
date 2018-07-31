package com.md.member.warpper;

import java.util.List;
import java.util.Map;

import com.md.member.factory.MemberFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 余额的包装类
 *
 * @authr 
 * @date 2017年2月19日15:07:29
 */
public class BalanceWarpper extends BaseControllerWarpper {

    public BalanceWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("memberName", MemberFactory.me().getMemberName((Long)map.get("memberId")));
    	map.put("phoneNum",MemberFactory.me().getPhoneNum((Long)map.get("memberId")));
    }

}
