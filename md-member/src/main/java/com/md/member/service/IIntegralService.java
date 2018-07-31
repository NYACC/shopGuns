package com.md.member.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.member.model.Integral;
import com.md.member.model.Member;

public interface IIntegralService extends IService<Integral> {
	/**
	 * 查找积分列表
	 * 
	 * @param integral
	 * @return
	 */
	List<Map<String, Object>> find(Integral integral);

	/**
	 * 添加积分
	 * 
	 * @param integral
	 */
	void add(Integral integral);

	/**
	 * 修改积分
	 * 
	 * @param integral
	 */
	void update(Integral integral);

	/**
	 * 通过id查找积分
	 * 
	 * @param id
	 * @return
	 */
	Integral getById(Long id);

	/**
	 * 初始化一个账户
	 * 
	 * @param member
	 * @return
	 */
	Integral init(Member member);
}
