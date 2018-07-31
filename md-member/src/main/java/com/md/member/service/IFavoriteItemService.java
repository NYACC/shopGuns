package com.md.member.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.member.model.FavoriteItem;

public interface IFavoriteItemService extends IService<FavoriteItem> {

	List<Map<String, Object>> selectFavoriteItemByfavoriteId(Long favoriteId);

}
