package com.stylefeng.guns.member;

import com.md.member.dao.IntegralMapper;
import com.md.member.dao.MemberMapper;
import com.md.member.model.Integral;
import com.md.member.model.Member;
import com.md.member.service.IIntegralService;
import com.stylefeng.guns.base.BaseJunit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegralTest extends BaseJunit {
	@Resource
	IIntegralService integralService;
	@Resource
	IntegralMapper integralMapper;
	Integral integral1;
	Integral integral2;
	Integral integral3;
	List<Integral> list = new ArrayList<>();
	Integral integral;
	@Resource
	MemberMapper memberMapper;
	@Before
	public void init(){
		integral = new Integral();
		integral1 = new Integral();
		integral1.setId(1L);
		integral1.setMemberId(1L);
		integral1.setIntegralValue(100);
		integral1.setSn(String.valueOf(new Date().getTime()));
		list.add(integral1);
		integralMapper.insert(integral1);

		integral2 = new Integral();
		integral2.setId(2L);
		integral2.setMemberId(2L);
		integral2.setIntegralValue(200);
		integral2.setSn(String.valueOf(new Date().getTime()));
		list.add(integral2);
		integralMapper.insert(integral2);

		integral3 = new Integral();
		integral3.setId(3L);
		integral3.setMemberId(3L);
		integral3.setIntegralValue(300);
		integral3.setSn(String.valueOf(new Date().getTime()));
		list.add(integral3);
		integralMapper.insert(integral3);
	}
	@After
	public void destroy(){
		integralMapper.deleteById(integral1.getId());
		integralMapper.deleteById(integral2.getId());
		integralMapper.deleteById(integral3.getId());

		integral1 = null;
		integral2 = null;
		integral3 = null;

		integral = null;

		list = null;
	}

	/**
	 * 根据会员手机号查询
	 */
	@Test
	public void findListByPhoneNum(){
		Member member = new Member();
		member.setPhoneNum("15464646841");
		Member member1 = memberMapper.selectOne(member);
		integral.setMemberId(member1.getId());
		long expected = list.stream().filter(i -> i.getMemberId() == member1.getId()).count();
		long actual = integralService.find(integral).stream().filter(i->i.get("memberId") == member1.getId()).count();
		assertEquals(expected, actual);
	}

}
