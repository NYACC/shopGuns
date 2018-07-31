package com.md.member.factory;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.md.member.dao.CardLevelMapper;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 会员卡创建工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class MemberCardFactory {
    private CardLevelMapper cardLevelMapper=SpringContextHolder.getBean(CardLevelMapper.class);
    public static MemberCardFactory me() {
        return SpringContextHolder.getBean(MemberCardFactory.class);
    }

    /**
     * 根据会员的编号获取会员的昵称
     * @return
     */
    public String getCardlevelName(Long id){
        return cardLevelMapper.selectById(id).getName();
    }
    
}
