package com.md.settlement.exception;

import com.stylefeng.guns.core.exception.ServiceExceptionEnum;

/**
 * @Description 异常的枚举
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum SettlementExceptionEnum implements ServiceExceptionEnum{

	NO_DELIVERYCOST(900,"对不起，门店不支持配送"),
	COUPONCODE_USED(901,"对不起，该优惠卷已被使用过"),
	COUPON_OVERDUE(901,"对不起，该优惠卷无法使用");

	SettlementExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private Integer code;

	private String message;

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

