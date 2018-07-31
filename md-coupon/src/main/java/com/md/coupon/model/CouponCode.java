package com.md.coupon.model;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_coupon_code")
public class CouponCode {
	/**
	 * 主键id创建映射
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	//优惠码
	private String code;
	//所属优惠的id
	private Long couponId;
	//状态
	private Integer status;
	//领取时间
	private Timestamp receviceTime;
	//领取用户Id
	private Long memberId;
	//使用日期
	private Timestamp validityTime;
	//创建日期
	private Timestamp createTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getReceviceTime() {
		return receviceTime;
	}
	public void setReceviceTime(Timestamp receviceTime) {
		this.receviceTime = receviceTime;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Timestamp getValidityTime() {
		return validityTime;
	}
	public void setValidityTime(Timestamp validityTime) {
		this.validityTime = validityTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
