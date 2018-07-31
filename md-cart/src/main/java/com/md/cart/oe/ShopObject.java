package com.md.cart.oe;

import java.util.List;


public class ShopObject {

	/**
	 * 门店id
	 */
	private Long shopid;

	/**
	 * 门店名称
	 */
	private String shopName;

	/**
	 * 门店下购物车项
	 */
	private List<GoodObject> goodItems;

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public List<GoodObject> getGoodItems() {
		return goodItems;
	}

	public void setGoodItems(List<GoodObject> goodItems) {
		this.goodItems = goodItems;
	}

}
