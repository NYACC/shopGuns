package com.stylefeng.guns.member;

import com.stylefeng.guns.base.BaseJunit;
import com.md.member.dao.MemberMapper;
import com.md.member.model.Member;
import com.md.member.service.IMemberService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MemberTest extends BaseJunit {
	@Resource
	IMemberService memberService;
	@Resource
	MemberMapper memberMapper;
	Member member1;
	Member member2;
	Member member3;
	List<Member> list = new ArrayList<>();
	Member member;
	@Before
	public void init(){
		member = new Member();
		member1 = new Member();
		member1.setId(1L);
		member1.setName("张1");
		member1.setPhoneNum("11111111111");
		list.add(member1);
		memberMapper.insert(member1);

		member2 = new Member();
		member2.setId(2L);
		member2.setName("张2");
		member2.setPhoneNum("2222222222");
		list.add(member2);
		memberMapper.insert(member2);

		member3 = new Member();
		member3.setId(3L);
		member3.setName("张3");
		member3.setPhoneNum("33333333333");
		list.add(member3);
		memberMapper.insert(member3);
	}
	@After
	public void destroy(){
		memberMapper.deleteById(member1.getId());
		memberMapper.deleteById(member2.getId());
		memberMapper.deleteById(member3.getId());

		member1 = null;
		member2 = null;
		member3 = null;
		list = null;
		member = null;
	}
	/**
	 * 根据会员昵称查询
	 */
	@Test
	public void findListByName(){
		member.setName("张3");
		long expected = list.stream().filter(m -> m.getName().equals(member.getName())).count();
		long actual = memberService.find(member).stream().filter(m->m.get("name").equals(member.getName())).count();
		assertEquals(expected, actual);
	}

	/**
	 * 根据会员手机号查询
	 */
	@Test
	public void findListByPhoneNum(){
		member.setPhoneNum("11111111111");
		long expected = list.stream().filter(m -> m.getPhoneNum().equals(member.getPhoneNum())).count();
		long actual = memberService.find(member).stream().filter(m->m.get("phoneNum").equals(member.getPhoneNum())).count();
		assertEquals(expected, actual);
	}

	/**
	 * 根据会员手机号和会员昵称查询
	 */
	@Test
	public void findListByPhoneNumAndName(){
		member.setName("张");
		member.setPhoneNum("1111111");
		long expected = list.stream().filter(m -> m.getPhoneNum().equals(member.getPhoneNum())&&m.getName().equals(member.getName())).count();
		long actual = memberService.find(member).stream().filter(m->m.get("phoneNum").equals(member.getPhoneNum())&&m.get("name").equals(member.getName())).count();
		assertEquals(expected, actual);
	}
	

}
