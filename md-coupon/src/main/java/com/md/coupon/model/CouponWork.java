package com.md.coupon.model;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_coupon_work")
public class CouponWork {
	/**
	 * 主键id创建映射
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	//优惠卷Id
	private Long couponId;
	//生成数量
	private Integer quantity;
	//生成时间
	private Timestamp produceTime;
	//创建时间
	private Timestamp createTime;
	//是否执行
	private Boolean executed;
	//备注
	private String remark;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Timestamp getProduceTime() {
		return produceTime;
	}
	public void setProduceTime(Timestamp produceTime) {
		this.produceTime = produceTime;
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
	public Boolean getExecuted() {
		return executed;
	}
	public void setExecuted(Boolean executed) {
		this.executed = executed;
	}
	
}
