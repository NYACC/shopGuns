package com.md.member.service.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.member.dao.BalanceMapper;
import com.md.member.model.Balance;
import com.md.member.model.Member;
import com.md.member.service.IBalanceService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class BalanceServiceImpl extends ServiceImpl<BalanceMapper, Balance> implements IBalanceService {

	@Resource
	BalanceMapper balanceMapper;

	@Override
	public List<Map<String, Object>> find(Balance balance) {
		Wrapper<Balance> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(balance.getMemberId())) {
			wrapper.eq("memberId", balance.getMemberId());
		}
		List<Map<String, Object>> balances = balanceMapper.selectMaps(wrapper);
		return balances;
	}

	@Override
	public void recharge(Balance balance, BigDecimal money) {
		Balance rBalance = balanceMapper.selectById(balance.getId());
		rBalance.setBalance(rBalance.getBalance().add(money));
		balanceMapper.updateById(rBalance);
	}

	@Override
	public Balance init(Member member) {
		Balance balance = new Balance();
		balance.setBalanceSn(String.valueOf(new Date().getTime()));
		balance.setBalance(new BigDecimal("0"));
		balance.setMemberId(member.getId());
		balanceMapper.insert(balance);
		return balance;
	}

	@Override
	public Balance getById(Long id) {
		Balance balance = balanceMapper.selectById(id);
		return balance;
	}

}
