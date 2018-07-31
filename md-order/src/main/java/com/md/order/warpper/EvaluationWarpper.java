package com.md.order.warpper;

import java.util.List;
import java.util.Map;

import com.md.member.factory.MemberFactory;
import com.md.order.constant.EvaluationLevel;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 评价列表的包装类
 *
 * @auth r fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class EvaluationWarpper extends BaseControllerWarpper {

    public EvaluationWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("memberName", MemberFactory.me().getMemberName((Long)map.get("memberId")));
    	map.put("levelName", EvaluationLevel.valueOf((Integer)map.get("level")));
    }

}
