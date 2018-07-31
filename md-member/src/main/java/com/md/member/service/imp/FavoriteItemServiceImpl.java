package com.md.member.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.member.dao.FavoriteItemMapper;
import com.md.member.model.FavoriteItem;
import com.md.member.service.IFavoriteItemService;

@Service
public class FavoriteItemServiceImpl extends ServiceImpl<FavoriteItemMapper, FavoriteItem> implements IFavoriteItemService {

	@Resource
	FavoriteItemMapper favoriteItemMapper;
	
	@Override
	public List<Map<String, Object>> selectFavoriteItemByfavoriteId(Long favoriteId) {
		// TODO 自动生成的方法存根	
		return favoriteItemMapper.selectFavoriteItemByfavoriteId(favoriteId);
	}

	

}
