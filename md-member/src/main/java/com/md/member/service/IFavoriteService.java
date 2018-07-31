package com.md.member.service;

import com.baomidou.mybatisplus.service.IService;
import com.md.member.model.Favorite;
import com.md.member.model.Member;

public interface IFavoriteService extends IService<Favorite> {

	/**
	 * 初始化一个收藏夹
	 * @param member
	 */
	Favorite init(Member member);
	
	Favorite selectByMemberId(Long memberId);

}
