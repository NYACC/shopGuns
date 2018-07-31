package com.md.delivery.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_delivery_cost")
public class DeliveryCost {
	/**
	 * 主键id创建映射
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	//派货地区
	private Long deliveryArea;
	//收货地区
	private Long areaId;
	//所属配送方式
	private Long modeId;
	//所属配送方式
	private Boolean isdelivery;
	//首重
	private BigDecimal ykg;
	//首价
	private BigDecimal startPrice;
	//续重
	private BigDecimal addedWeight;
	//续价
	private BigDecimal addedPrice;
	//创建时间
	private Timestamp createTime;
	//修改时间
	private Timestamp modifyTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public BigDecimal getYkg() {
		return ykg;
	}
	public void setYkg(BigDecimal ykg) {
		this.ykg = ykg;
	}
	public BigDecimal getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}
	public BigDecimal getAddedWeight() {
		return addedWeight;
	}
	public void setAddedWeight(BigDecimal addedWeight) {
		this.addedWeight = addedWeight;
	}
	public BigDecimal getAddedPrice() {
		return addedPrice;
	}
	public void setAddedPrice(BigDecimal addedPrice) {
		this.addedPrice = addedPrice;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Long getModeId() {
		return modeId;
	}
	public void setModeId(Long modeId) {
		this.modeId = modeId;
	}
	public Boolean getIsdelivery() {
		return isdelivery;
	}
	public void setIsdelivery(Boolean isdelivery) {
		this.isdelivery = isdelivery;
	}
	public Long getDeliveryArea() {
		return deliveryArea;
	}
	public void setDeliveryArea(Long deliveryArea) {
		this.deliveryArea = deliveryArea;
	}
	
	
}
