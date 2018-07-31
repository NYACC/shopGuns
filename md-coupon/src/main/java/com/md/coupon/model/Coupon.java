package com.md.coupon.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_coupon")
public class Coupon {
	/**
	 * 主键id创建映射
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	//名称
	private String name;
	//使用开始时间
	private Timestamp useStart;
	//使用结束时间
	private Timestamp useEnd;
	//开始领取的时间
	private Timestamp receiveStart;
	//领取结束时间
	private Timestamp receiveEnd;
	//满足优惠的金额
	private BigDecimal fulfil;
	//优惠方式：1 可领取 ，2 只能赠送
	private Integer model;
	//减少的金额
	private BigDecimal reduce;
	//折扣
	private BigDecimal discount;
	//创建时间
	private Timestamp createTime;
	//备注
	private String remark;
	//门店id
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
	public Timestamp getUseStart() {
		return useStart;
	}
	public void setUseStart(Timestamp useStart) {
		this.useStart = useStart;
	}
	public Timestamp getUseEnd() {
		return useEnd;
	}
	public void setUseEnd(Timestamp useEnd) {
		this.useEnd = useEnd;
	}
	public Timestamp getReceiveStart() {
		return receiveStart;
	}
	public void setReceiveStart(Timestamp receiveStart) {
		this.receiveStart = receiveStart;
	}
	public Timestamp getReceiveEnd() {
		return receiveEnd;
	}
	public void setReceiveEnd(Timestamp receiveEnd) {
		this.receiveEnd = receiveEnd;
	}
	public BigDecimal getFulfil() {
		return fulfil;
	}
	public void setFulfil(BigDecimal fulfil) {
		this.fulfil = fulfil;
	}
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	public BigDecimal getReduce() {
		return reduce;
	}
	public void setReduce(BigDecimal reduce) {
		this.reduce = reduce;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
}
