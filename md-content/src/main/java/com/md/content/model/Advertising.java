package com.md.content.model;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_advertising")
public class Advertising {
	/**
	 * 主键id创建映射
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	//名字
	private String name;
	//图片
	private String picture;
	//链接
	private String link;
	//关联的商品
	private String priceTagIds;
	//排序
	private Long positionId;
	//排序
	private Integer sequence;
	//创建时间
	private Timestamp createTime;
	//所属门店
	private Long shopId;
	
	
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getPriceTagIds() {
		return priceTagIds;
	}
	public void setPriceTagIds(String priceTagIds) {
		this.priceTagIds = priceTagIds;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
}