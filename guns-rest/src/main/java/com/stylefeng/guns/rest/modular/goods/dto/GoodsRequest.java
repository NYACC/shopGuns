package com.stylefeng.guns.rest.modular.goods.dto;

/**
 * 商品
 * 
 * @author 54476
 *
 */
public class GoodsRequest {

	private Long cateId;

	private Long shopId;

	private Integer index;

	private String name;

	private Long goodsId;

	private Long tagId;
	
	private Long priceTagId;

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getPriceTagId() {
		return priceTagId;
	}

	public void setPriceTagId(Long priceTagId) {
		this.priceTagId = priceTagId;
	}

}
