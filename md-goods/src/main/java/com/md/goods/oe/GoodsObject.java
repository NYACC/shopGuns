package com.md.goods.oe;

import java.math.BigDecimal;

/**
 * 商品oe
 * 
 * @author 54476
 *
 */
public class GoodsObject {

	/**
	 * 商品id
	 */
	private Long id;

	/**
	 * 商品名称
	 */
	private String goodName;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 品牌名称
	 */
	private String brandName;

	/**
	 * 市场价
	 */
	private BigDecimal marketPrice;

	/**
	 * 销售价
	 */
	private BigDecimal price;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 商品图
	 */
	private String images;

	/**
	 * 商品库存
	 */
	private Long stock;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

}
