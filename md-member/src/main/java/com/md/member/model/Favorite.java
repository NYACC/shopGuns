package com.md.member.model;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_favorite")
public class Favorite {
	// id
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	// 所属用户Id
	private Long memberId;
	// 收藏项集合
	@TableField(exist = false)
	private List<FavoriteItem> favoriteItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public List<FavoriteItem> getFavoriteItem() {
		return favoriteItem;
	}

	public void setFavoriteItem(List<FavoriteItem> favoriteItem) {
		this.favoriteItem = favoriteItem;
	}

}
