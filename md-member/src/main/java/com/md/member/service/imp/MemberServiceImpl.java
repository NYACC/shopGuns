package com.md.member.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.member.dao.MemberMapper;
import com.md.member.model.Member;
import com.md.member.service.IMemberService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

	@Resource
	MemberMapper memberMapper;

	@Override
	public List<Map<String, Object>> find(Member member) {
		Wrapper<Member> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(member)) {
			if (ToolUtil.isNotEmpty(member.getName())) {
				wrapper.like("name", member.getName());
			}
			if (ToolUtil.isNotEmpty(member.getPhoneNum())) {
				wrapper.like("phoneNum", member.getPhoneNum());
			}
			if (ToolUtil.isNotEmpty(member.getOpenId())) {
				wrapper.like("openId", member.getOpenId());
			}
		}
		return memberMapper.selectMaps(wrapper);
	}

	@Override
	public void add(Member member) {
		memberMapper.insert(member);
	}

	@Override
	public Member findById(Long id) {
		return memberMapper.selectById(id);
	}

	@Override
	public void update(Member member) {
		memberMapper.updateById(member);
	}

	@Override
	public List<Member> selectByPhoneNum(String phoneNum) {
		// TODO 自动生成的方法存根
		Wrapper<Member> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(phoneNum)) {
			wrapper.eq("phoneNum", phoneNum);	
			return memberMapper.selectList(wrapper);
		}
		return null;
	}
}
