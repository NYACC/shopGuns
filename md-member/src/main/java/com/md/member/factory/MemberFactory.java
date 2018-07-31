package com.md.member.factory;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.stylefeng.guns.core.support.StrKit;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.md.member.dao.MemberMapper;
import com.md.member.model.Member;

/**
 * 客户创建工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class MemberFactory {
    
	private MemberMapper memberMapper = SpringContextHolder.getBean(MemberMapper.class);
    
    public static MemberFactory me() {
        return SpringContextHolder.getBean(MemberFactory.class);
    }

    /**
     * 获取顾客的ids(多个)
     */
    public String getIds(List<Map<String,Object>> customers) {
        if(ToolUtil.isEmpty(customers)){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        customers.stream().forEach(c->{
            sb.append(c.get("id")).append(",");
        });
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 根据会员的编号获取会员的昵称
     * @return
     */
    public String getMemberName(Long id){
        if(ToolUtil.isNotEmpty(id)){
            return memberMapper.selectById(id).getName();
        }
        return null;
    }

    /**
     * 根据会员编号查找会员手机号
     * @param id
     * @return
     */
    public String getPhoneNum(Long id){
        if(ToolUtil.isNotEmpty(id)){
            return memberMapper.selectById(id).getPhoneNum();
        }
        return null;
    }
    
    public String getPhoneNumByOpenId(String openId){
    	Wrapper<Member> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(openId)){
			wrapper.eq("openId", openId);
		}
		List<Member> members = memberMapper.selectList(wrapper);
		if(members.size() > 0 ){
			return members.get(0).getPhoneNum();
		}
		return null;
    }
    
}
