package com.md.member.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.member.dao.MemberCardMapper;
import com.md.member.model.Member;
import com.md.member.model.MemberCard;
import com.md.member.service.IMemberCardService;
import com.stylefeng.guns.core.constant.Status;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCard> implements IMemberCardService {

	@Resource
	MemberCardMapper memberCardMapper;

	@Override
	public List<Map<String, Object>> find(MemberCard memberCard) {
		Wrapper<MemberCard> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(memberCard)) {
			if (ToolUtil.isNotEmpty(memberCard.getCardSn())) {
				wrapper.like("cardSn", memberCard.getCardSn());
			}
			if (ToolUtil.isNotEmpty(memberCard.getCardLevelId())) {
				wrapper.eq("cardLevelId", memberCard.getCardLevelId());
			}
			if (ToolUtil.isNotEmpty(memberCard.getStatus())) {
				wrapper.eq("status", memberCard.getStatus());
			}
		}
		return memberCardMapper.selectMaps(wrapper);
	}

	@Override
	public MemberCard getMemberCard(Long id) {
		return memberCardMapper.selectById(id);
	}

	@Override
	public void add(MemberCard memberCard) {
		memberCardMapper.insert(memberCard);
	}

	@Override
	public void update(MemberCard memberCard) {
		memberCardMapper.updateById(memberCard);
	}

	@Override
	public MemberCard init(Member member, Long cardLevel) {
		// 寻找该等级的空卡
		Wrapper<MemberCard> wrapper = new EntityWrapper<>();
		wrapper.eq("cardLevelId", cardLevel);
		wrapper.eq("status", Status.DISABLE.getCode());
		List<MemberCard> selectList = memberCardMapper.selectList(wrapper);
		if (selectList.size() > 0) {
			// 如果有该等级空卡，修改卡的属性
			MemberCard card = selectList.get(0);
			card.setMemberId(member.getId());
			card.setStatus(Status.ENABLED.getCode());
			this.update(card);
			return card;
		} else {
			// 如果没有该等级的空卡，创建新的一张新卡
			MemberCard card = new MemberCard();
			card.setCardSn(String.valueOf(new Date().getTime()));
			card.setCardLevelId(cardLevel);
			card.setStatus(Status.ENABLED.getCode());
			card.setMemberId(member.getId());
			this.add(card);
			return card;
		}
	}
}
