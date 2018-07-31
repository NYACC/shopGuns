package com.stylefeng.guns.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.md.member.dao.MemberCardMapper;
import com.md.member.model.Member;
import com.md.member.model.MemberCard;
import com.md.member.service.IMemberCardService;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.core.constant.Status;


public class MemberCardTest extends BaseJunit {
	@Resource
	IMemberCardService memberCardService;
	@Resource
	MemberCardMapper memberCardMapper;
	MemberCard memberCard1;
	MemberCard memberCard2;
	MemberCard memberCard3;
	List<MemberCard> list = new ArrayList<>();
	MemberCard memberCard;
	@Before
	public void init(){
		memberCard = new MemberCard();
		memberCard1 = new MemberCard();
		memberCard1.setId(1L);
		memberCard1.setCardSn("1111");
		memberCard1.setCardLevelId(1L);
		memberCard1.setStatus(Status.DISABLE.getCode());
		list.add(memberCard1);
		memberCardMapper.insert(memberCard1);

		memberCard2 = new MemberCard();
		memberCard2.setId(2L);
		memberCard2.setCardSn("2222");
		memberCard2.setCardLevelId(2L);
		memberCard2.setStatus(Status.ENABLED.getCode());
		list.add(memberCard2);
		memberCardMapper.insert(memberCard2);

		memberCard3 = new MemberCard();
		memberCard3.setId(3L);
		memberCard3.setCardSn("3333");
		memberCard3.setCardLevelId(3L);
		memberCard3.setStatus(Status.DISABLE.getCode());
		list.add(memberCard3);
		memberCardMapper.insert(memberCard3);
	}
	@After
	public void destroy(){
		memberCardMapper.deleteById(memberCard1.getId());
		memberCardMapper.deleteById(memberCard2.getId());
		memberCardMapper.deleteById(memberCard3.getId());

		memberCard1 = null;
		memberCard2 = null;
		memberCard3 = null;

		list = null;
		memberCard = null;
	}
	/**
	 * 测试根据卡号查询
	 */
	@Test
	public void findListBySn(){
		memberCard.setCardSn("1111");
		System.out.println(list.stream().filter(m -> m.getCardSn().contains(memberCard.getCardSn())));
		long expected = list.stream().filter(m -> m.getCardSn().contains(memberCard.getCardSn())).count();
		long actual = memberCardService.find(memberCard).stream().filter(m->m.get("cardSn").equals(memberCard.getCardSn())).count();
		assertEquals(expected, actual);
	}

	/**
	 *
	 *测试根据等级查询
	 */
	@Test
	public void findListByLevel(){
		memberCard.setCardLevelId(2L);
		long expected = list.stream().filter(m -> m.getCardLevelId()==memberCard.getCardLevelId()).count();
		long actual = memberCardService.find(memberCard).stream().filter(m->m.get("cardLevelId")==memberCard.getCardLevelId()).count();
		assertEquals(expected, actual);
	}

	/**
	 *
	 *测试根据状态查询
	 */
	@Test
	public void findListByStatus(){
		memberCard.setStatus(0);
		long expected = list.stream().filter(m -> m.getStatus()==memberCard.getStatus()).count();
		long actual = memberCardService.find(memberCard).stream().filter(m->m.get("status")==memberCard.getStatus()).count();
		assertEquals(expected, actual);
	}
	/**
	 * 测试根据卡号、等级、状态查询
	 */
	@Test
	public void findListBySnAndLevelAndStatus(){
		memberCard.setCardSn("1111");
		memberCard.setCardLevelId(1L);
		memberCard.setStatus(0);
		long expected = list.stream().filter(m -> m.getCardSn().equals(memberCard.getCardSn())&&m.getCardLevelId()==memberCard.getCardLevelId()&&m.getStatus().equals(memberCard.getStatus())).count();
		long actual = memberCardService.find(memberCard).stream().filter(m->m.get("cardSn").equals(memberCard.getCardSn())&&m.get("cardLevelId")==memberCard.getCardLevelId()&&m.get("status")==memberCard.getStatus()).count();
		assertEquals(expected, actual);
	}
	
	/**
	 * 测试获取获取一张等级为1L的卡，预期结果：有空卡，卡号=1111
	 */
	@Test
	public void init_cardLevel1L(){
		Member member = new Member();
		member.setId(1L);
		MemberCard card = memberCardService.init(member, 1L);
		assertEquals("1111", card.getCardSn());
		member=null;
	} 
	
	/**
	 * 测试获取获取一张等级为2L的卡，预期结果：不存在空卡，卡号不为空
	 */
	@Test
	public void init_cardLevel2L(){
		Member member = new Member();
		member.setId(1L);
		MemberCard card = memberCardService.init(member, 2L);
		assertTrue(card!=null);
		if(card!=null){
			memberCardMapper.deleteById(card.getId());
		}
		member=null;
	}

}
