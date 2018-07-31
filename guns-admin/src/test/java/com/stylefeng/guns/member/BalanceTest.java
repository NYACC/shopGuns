package com.stylefeng.guns.member;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.md.member.dao.BalanceMapper;
import com.md.member.model.Balance;
import com.md.member.service.IBalanceService;
import com.stylefeng.guns.base.BaseJunit;

public class BalanceTest extends BaseJunit {
	
	@Resource
	IBalanceService balanceService;
	@Resource
	BalanceMapper balanceMapper;
	
	private Balance balance1;
	private Balance balance2;
	private Balance balance3;
	
	private Balance queryObj;
	
	@Before
	public void init(){
		queryObj=new Balance();
		
		balance1=new Balance();
		balance1.setId(1L);
		balance1.setBalanceSn("10001");
		balance1.setMemberId(1L);
		balance1.setBalance(new BigDecimal("100"));
		balanceMapper.insert(balance1);
		
		balance2=new Balance();
		balance2.setId(2L);
		balance2.setBalanceSn("10002");
		balance2.setMemberId(11L);
		balance2.setBalance(new BigDecimal("1000"));
		balanceMapper.insert(balance2);

		balance3=new Balance();
		balance3.setId(3L);
		balance3.setBalanceSn("10003");
		balance3.setMemberId(12L);
		balance3.setBalance(new BigDecimal("10"));
		balanceMapper.insert(balance3);
	}
	@After
	public void destroy(){
		balanceMapper.deleteById(1L);
		balanceMapper.deleteById(2L);
		balanceMapper.deleteById(3L);
		
		balance1=null;
		balance2=null;
		balance3=null;
		queryObj=null;
	}
	/**
	 * 根据用户id查询
	 */
	@Test
	public void find_memberId11(){
		queryObj.setMemberId(1L);
		Integer expected = 1;
		Integer actual = balanceService.find(queryObj).size();
		assertEquals(expected, actual);
	}
	
	/**
	 * 根据用户id查询
	 */
	@Test
	public void find_memberId15(){
		queryObj.setMemberId(15L);
		Integer expected = 0;
		Integer actual = balanceService.find(queryObj).size();
		assertEquals(expected, actual);
	}

	/**
	 * 给余额账号1充值100元
	 */
	@Test
	public void recharge_member1_100(){
		BigDecimal expected=new BigDecimal("200");
		balanceService.recharge(balance1,new BigDecimal("100"));
		Balance balance = balanceMapper.selectById(1L);
		assertEquals(expected, balance.getBalance());
	}
	
	/**
	 * 给余额账号2充值200元
	 */
	@Test
	public void recharge_member2_200(){
		BigDecimal expected=new BigDecimal("1200");
		balanceService.recharge(balance2,new BigDecimal("200"));
		Balance balance = balanceMapper.selectById(2L);
		assertEquals(expected, balance.getBalance());
	}
}
