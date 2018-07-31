package com.md.member.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_address")
public class Address {
	/**
	 * 主键id
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	//收货人姓名
	private String consigeeName;
	//联系电话
	private String phone;
	//省id
	private Long province;
	//市id
	private Long city;
	//区id
	private Long county;
	//省市区名称
	private String addressName;
	//详细地址
	private String detail;
	//所属用户
	private Long memberId;
	//是否默认
	private Boolean isdefault;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConsigeeName() {
		return consigeeName;
	}
	public void setConsigeeName(String consigeeName) {
		this.consigeeName = consigeeName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getProvince() {
		return province;
	}
	public void setProvince(Long province) {
		this.province = province;
	}
	public Long getCity() {
		return city;
	}
	public void setCity(Long city) {
		this.city = city;
	}
	public Long getCounty() {
		return county;
	}
	public void setCounty(Long county) {
		this.county = county;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public Boolean getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(Boolean isdefault) {
		this.isdefault = isdefault;
	}
}
