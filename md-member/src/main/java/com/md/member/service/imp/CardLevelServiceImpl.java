package com.md.member.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.member.dao.CardLevelMapper;
import com.md.member.model.CardLevel;
import com.md.member.service.ICardLevelService;

@Service
public class CardLevelServiceImpl extends ServiceImpl<CardLevelMapper, CardLevel> implements ICardLevelService {

	@Resource
	CardLevelMapper cardLevelMapper;

	@Override
	public List<Map<String, Object>> list() {
		Wrapper<CardLevel> wrapper = new EntityWrapper<>();
		wrapper.orderBy("num", true);
		List<Map<String, Object>> selectMaps = cardLevelMapper.selectMaps(wrapper);
		return selectMaps;
	}

	@Override
	public void add(CardLevel cardLevel) {
		cardLevelMapper.insert(cardLevel);
	}

	@Override
	public CardLevel getById(Long cardLevelId) {
		CardLevel cardLevel = cardLevelMapper.selectById(cardLevelId);
		return cardLevel;
	}

	@Override
	public void update(CardLevel cardLevel) {
		cardLevelMapper.updateById(cardLevel);
	}

	@Override
	public void deleteById(Long cardLevelId) {
		cardLevelMapper.deleteById(cardLevelId);
	}

}
