package com.md.member.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.member.dao.IntegralMapper;
import com.md.member.model.Integral;
import com.md.member.model.Member;
import com.md.member.service.IIntegralService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class IntegralServiceImpl extends ServiceImpl<IntegralMapper, Integral> implements IIntegralService {

	@Resource
	IntegralMapper integralMapper;

	@Override
	public List<Map<String, Object>> find(Integral integral) {
		Wrapper<Integral> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(integral)) {
			wrapper.eq("memberId", integral.getMemberId());
		}
		return integralMapper.selectMaps(wrapper);
	}

	@Override
	public void add(Integral integral) {
		integralMapper.insert(integral);
	}

	@Override
	public void update(Integral integral) {
		integralMapper.updateById(integral);
	}

	@Override
	public Integral getById(Long id) {
		return integralMapper.selectById(id);
	}

	@Override
	public Integral init(Member member) {
		Integral integral = new Integral();
		integral.setSn(String.valueOf(new Date().getTime()));
		integral.setIntegralValue(0);
		integral.setMemberId(member.getId());
		integralMapper.insert(integral);
		return integral;
	}

}
