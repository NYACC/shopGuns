package com.md.member.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.member.dao.FavoriteMapper;
import com.md.member.model.Favorite;
import com.md.member.model.Member;
import com.md.member.service.IFavoriteService;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {

	@Resource
	FavoriteMapper favoriteMapper;

	@Override
	public Favorite init(Member member) {
		Favorite favorite = new Favorite();
		favorite.setMemberId(member.getId());
		favoriteMapper.insert(favorite);
		return favorite;
	}

	@Override
	public Favorite selectByMemberId(Long memberId) {
		// TODO 自动生成的方法存根
		Wrapper<Favorite> wrapper = new EntityWrapper<Favorite>();
		wrapper.eq("memberId", memberId);
		List<Favorite> favoriteList = favoriteMapper.selectList(wrapper);
		if (favoriteList.size() == 0) {
			return null;
		}
		return favoriteList.get(0);
	}
}
