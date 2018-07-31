package com.md.member.service;

import com.baomidou.mybatisplus.service.IService;
import com.md.member.model.Member;

import java.util.List;
import java.util.Map;

public interface IMemberService extends IService<Member> {
	/**
	 * 查找客户
	 * 
	 * @param member
	 * @return
	 */
	List<Map<String, Object>> find(Member member);

	/**
	 * 添加客户
	 * 
	 * @param member
	 */
	void add(Member member);

	/**
	 * 通过id查找用户
	 * 
	 * @param id
	 * @return
	 */
	Member findById(Long id);

	/**
	 * 修改客户
	 * 
	 * @param member
	 */
	void update(Member member);

	/**
	 * 通过手机号码查找用户
	 * @param phoneNum
	 * @return
	 */
	List<Member> selectByPhoneNum(String phoneNum);
}
