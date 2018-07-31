package com.stylefeng.guns.rest.modular.category.dto;

import java.util.List;

import com.md.goods.model.Category;
import com.md.goods.model.Goods;

public class CategoryResponse extends Category{
	
	private List<Goods> goodList;//类目下的商品

	public List<Goods> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<Goods> goodList) {
		this.goodList = goodList;
	}

}
