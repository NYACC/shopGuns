package com.stylefeng.guns.rest.modular.member.dto;

public class MemberRequest {
	// 客户id
	private Long memberId;
	// 是否默认地址
	private boolean isdefault;
	// 收货地址id
	private Long addressId;
	// 客户手机号
	private String phone;
	// 密码
	private String password;
	// 是否绑定会员卡
	private Boolean isCard;
	// 会员卡等级
	private Long cardLevel;
	// 验证码
	private String VerificationCode;
	// 旧密码
	private String oldPassword;
	// 收藏商品数组，样例1,2,3,
	private String itemIds;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public boolean isIsdefault() {
		return isdefault;
	}

	public void setIsdefault(boolean isdefault) {
		this.isdefault = isdefault;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsCard() {
		return isCard;
	}

	public void setIsCard(Boolean isCard) {
		this.isCard = isCard;
	}

	public Long getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(Long cardLevel) {
		this.cardLevel = cardLevel;
	}

	public String getVerificationCode() {
		return VerificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		VerificationCode = verificationCode;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

}
