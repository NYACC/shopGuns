package com.md.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.md.member.model.FavoriteItem;

public interface FavoriteItemMapper extends BaseMapper<FavoriteItem>{

	List<Map<String, Object>> selectFavoriteItemByfavoriteId(@Param("favoriteId") Long favoriteId);

}
