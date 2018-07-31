package com.md.member.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.member.model.CardLevel;

public interface ICardLevelService extends IService<CardLevel> {
	/**
	 * 获取会员卡等级列表
	 * 
	 * @param member
	 * @return
	 */
	List<Map<String, Object>> list();

	/**
	 * 添加会员卡等级
	 * 
	 * @param cardLevel
	 */
	void add(CardLevel cardLevel);

	/**
	 * 通过id获取会员卡等级对象
	 * 
	 * @param cardLevelId
	 * @return
	 */
	CardLevel getById(Long cardLevelId);

	/**
	 * 修改会员卡等级
	 * 
	 * @param cardLevel
	 */
	void update(CardLevel cardLevel);

	/**
	 * 通过id删除会员卡等级
	 * 
	 * @param cardLevelId
	 */
	void deleteById(Long cardLevelId);

}
