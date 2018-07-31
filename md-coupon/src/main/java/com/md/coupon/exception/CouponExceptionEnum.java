package com.md.coupon.exception;

import com.stylefeng.guns.core.exception.ServiceExceptionEnum;

/**
 * @Description 优惠的异常枚举
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum CouponExceptionEnum implements ServiceExceptionEnum{

	NOT_OPERABLE(900,"无法修改和删除");

	CouponExceptionEnum(int code, String message) {
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

