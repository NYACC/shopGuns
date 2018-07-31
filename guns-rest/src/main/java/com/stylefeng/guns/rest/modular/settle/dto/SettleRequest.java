package com.stylefeng.guns.rest.modular.settle.dto;

import java.util.List;

import com.md.delivery.model.DeliveryMode;
import com.md.member.model.Address;
import com.md.order.model.ShopItem;

public class SettleRequest {

	private List<ShopItem> shopItems;
	private Address address;
	private DeliveryMode deliveryMode;
	private List<ShopCoupon> shopCouponList;

	public List<ShopItem> getShopItems() {
		return shopItems;
	}

	public void setShopItems(List<ShopItem> shopItems) {
		this.shopItems = shopItems;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public DeliveryMode getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(DeliveryMode deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public List<ShopCoupon> getShopCouponList() {
		return shopCouponList;
	}

	public void setShopCouponList(List<ShopCoupon> shopCouponList) {
		this.shopCouponList = shopCouponList;
	}

}
