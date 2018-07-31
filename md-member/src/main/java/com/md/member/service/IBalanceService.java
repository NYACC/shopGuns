package com.md.member.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.member.model.Balance;
import com.md.member.model.Member;

public interface IBalanceService extends IService<Balance> {

	/**
	 * 查找余额账户列表
	 * 
	 * @param balance
	 * @return
	 */
	List<Map<String, Object>> find(Balance balance);

	/**
	 * 充值余额账户
	 * 
	 * @param balanceId
	 * @param balance
	 */
	void recharge(Balance balance, BigDecimal money);

	/**
	 * 初始化一个账户
	 * 
	 * @param member
	 * @return
	 */
	Balance init(Member member);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Balance getById(Long id);

}
