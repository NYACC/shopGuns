package com.md.goods.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_goods")
public class Goods {
	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	private String name;
	// 商品编号
	private String sn;
	// 品牌编号
	private Long brandId;
	// 商品单位
	private String unit;
	// 商品参数
	private String paramItems;
	// 商品规格
	private String specItems;
	// 商品图片组
	private String images;
	// 创建时间
	private Timestamp createTime;
	// 商品市场价
	private BigDecimal marketPrice;
	// 商品销售价
	private BigDecimal price;
	// 商品详情
	private String detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getParamItems() {
		return paramItems;
	}

	public void setParamItems(String paramItems) {
		this.paramItems = paramItems;
	}

	public String getSpecItems() {
		return specItems;
	}

	public void setSpecItems(String specItems) {
		this.specItems = specItems;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
