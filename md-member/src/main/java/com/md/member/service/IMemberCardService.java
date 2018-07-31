package com.md.member.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.member.model.Member;
import com.md.member.model.MemberCard;

public interface IMemberCardService extends IService<MemberCard>{
	/**
	 * 查找会员卡
	 * 
	 * @param memberCard
	 * @return
	 */
	List<Map<String, Object>> find(MemberCard memberCard);

	/**
	 * 通过id获取会员卡
	 * 
	 * @param id
	 * @return
	 */
	MemberCard getMemberCard(Long id);

	/**
	 * 添加会员卡
	 * 
	 * @param memberCard
	 */
	void add(MemberCard memberCard);

	/**
	 * 修改会员卡
	 * 
	 * @param memberCard
	 */
	void update(MemberCard memberCard);

	/**
	 * 获取一张空卡
	 * 
	 * @param member
	 * @param cardLevel
	 * @return
	 */
	MemberCard init(Member member, Long cardLevel);

}
