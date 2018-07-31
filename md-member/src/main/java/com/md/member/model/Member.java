package com.md.member.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_member")
public class Member {
	/**
	 * 主键id
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String name;
	private String phoneNum;
	private Integer sex;
	private String cardSn;
	private String password;
	private Integer status;
	private String integralSn;
	private String balanceSn;
	//微信id
	private String openId;
	//身份验证
	private String token;
	//短信验证码
	private String captcha;

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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCardSn() {
		return cardSn;
	}

	public void setCardSn(String cardSn) {
		this.cardSn = cardSn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIntegralSn() {
		return integralSn;
	}

	public void setIntegralSn(String integralSn) {
		this.integralSn = integralSn;
	}

	public String getBalanceSn() {
		return balanceSn;
	}

	public void setBalanceSn(String balanceSn) {
		this.balanceSn = balanceSn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
