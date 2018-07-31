package com.md.member.warpper;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.constant.Gender;
import com.stylefeng.guns.core.constant.Status;

/**
 * 客户的包装类
 *
 * @authr 
 * @date 2017年2月19日15:07:29
 */
public class MemberWarpper extends BaseControllerWarpper {

    public MemberWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("statusName", Status.valueOf((Integer)map.get("status")));
    	map.put("sexName", Gender.valueOf((Integer)map.get("sex")));
    	map.remove("password");
    }

}
