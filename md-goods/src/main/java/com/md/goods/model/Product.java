package com.md.goods.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

@TableName("shop_product")
public class Product {
	@TableId(value="id", type= IdType.AUTO)
    private Long id;
    //商品编号
    private Long goodsId;
    //规格的名称
    private String name;
    //重量
    private BigDecimal weight;
    //市场价格
    private BigDecimal marketPrice;
    //销售价格
    private BigDecimal price;
    //商品条码
    private String barcode;
    //规格项编号
    private String specItems;
    //规格商品图片
    private Long image;
    //规格商品编号
    private String sn;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getSpecItems() {
		return specItems;
	}
	public void setSpecItems(String specItems) {
		this.specItems = specItems;
	}
	public Long getImage() {
		return image;
	}
	public void setImage(Long image) {
		this.image = image;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}  
}
