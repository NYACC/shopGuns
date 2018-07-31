package com.md.order.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.md.goods.model.PriceTag;
import com.md.goods.model.Product;

@TableName("shop_order_item")
public class OrderItem {
	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	// 商品编号
	private Long goodsId;
	// 规格商品编号
	private Long productId;
	// 实际单价格
	private BigDecimal actualPrice;
	// 商品规格名称
	private String goodsName;
	// 商品单价
	private BigDecimal unitPrice;
	// 购买数量
	private Integer quantity;
	// 参与的促销
	private Long promotionId;
	// 小计
	private BigDecimal amount;
	// 所属订单编号
	private Long orderId;
	// 价格标签
	@TableField(exist = false)
	private PriceTag priceTag;
	// 产品的规格
	@TableField(exist = false)
	private Product product;

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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public PriceTag getPriceTag() {
		return priceTag;
	}

	public void setPriceTag(PriceTag priceTag) {
		this.priceTag = priceTag;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public OrderItem() {
		super();
	}

	public OrderItem(ShopItem shopItem, PriceTag priceTag) {
		super();
		// 商品编号
		this.goodsId = shopItem.getGoodsId();
		// 规格商品编号
		this.productId = shopItem.getProductId();
		// 商品单价
		this.unitPrice = priceTag.getPrice();
		// 实付价格
		this.actualPrice = priceTag.getPrice();
		// 商品规格名称
		this.goodsName = shopItem.getGoodsName();
		// 购买数量
		this.quantity = shopItem.getQuantity();
		// 小计
		this.amount = this.actualPrice.multiply(new BigDecimal(this.quantity)).setScale(2, BigDecimal.ROUND_HALF_UP);
		this.priceTag = priceTag;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
}
