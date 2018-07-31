package com.md.order.model;

public class ShopItem {
    private Long goodsId;
    private Long productId;
    private Integer quantity;
    private String goodsName;
    private Long shopId;
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public ShopItem() {
		super();
	}
	public ShopItem(Long goodsId, Long productId, Integer quantity, String goodsName,Long shopId) {
		super();
		this.goodsId = goodsId;
		this.productId = productId;
		this.quantity = quantity;
		this.goodsName = goodsName;
		this.shopId = shopId;
	}
}
