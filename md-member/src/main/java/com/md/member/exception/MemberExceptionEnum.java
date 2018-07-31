package com.md.member.exception;

import com.stylefeng.guns.core.exception.ServiceExceptionEnum;

/**
 * @Description 所有业务异常的枚举
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum MemberExceptionEnum implements ServiceExceptionEnum{

	DELETE_BRAND(900,"品牌下存在商品,请先删除商品"),
	INSERT_BRAND(901,"品牌名称已存在");

	MemberExceptionEnum(int code, String message) {
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

