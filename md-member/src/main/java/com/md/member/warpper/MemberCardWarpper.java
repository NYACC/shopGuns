package com.md.member.warpper;

import java.util.List;
import java.util.Map;

import com.md.member.factory.MemberCardFactory;
import com.md.member.factory.MemberFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.constant.Status;

/**
 * 会员卡的包装类
 *
 * @authr 
 * @date 2017年2月19日15:07:29
 */
public class MemberCardWarpper extends BaseControllerWarpper {

    public MemberCardWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("memberName", MemberFactory.me().getMemberName((Long)map.get("memberId")));
    	map.put("cardLevel", MemberCardFactory.me().getCardlevelName((Long)map.get("cardLevelId")));
    	map.put("status", Status.valueOf((Integer)map.get("status")));
    }

}
