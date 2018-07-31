package com.md.cart.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_cart_item")
public class CartItem {
	/**
	 * 主键id
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	//所属购物车Id
	private Long cartId;
	//所属商店Id
	private Long shopId;
	//规格商品编号
	private Long productId;
	//价格标签编号
	private Long priceTagId;
	//购买的数量
	private Integer quantity;
	//状态（有效 1，失效 0）
	private Integer status;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getPriceTagId() {
		return priceTagId;
	}
	public void setPriceTagId(Long priceTagId) {
		this.priceTagId = priceTagId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
