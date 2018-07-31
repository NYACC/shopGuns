package com.md.order.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

@TableName("shop_shipping_item")
public class ShippingItem {
	/**
	 * 主键id
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	//商品编号
	private	Long goodsId;
	//规格商品编号
	private	Long productId;
	//实际单价格
	private BigDecimal actualPrice;
	//商品规格名称
	private	String goodsName;
	//商品单价
	private	BigDecimal unitPrice;
	//小计
	private	BigDecimal amount;
	//购买数量
	private	Integer quantity;
	//发货数量
	private Integer shipQuantity;
	//所属发货单编号
	private Long shippingId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getShipQuantity() {
		return shipQuantity;
	}

	public void setShipQuantity(Integer shipQuantity) {
		this.shipQuantity = shipQuantity;
	}

	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}
}
