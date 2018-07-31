package com.md.content.exception;

import com.stylefeng.guns.core.exception.ServiceExceptionEnum;

/**
 * @Description 内容管理的异常枚举
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum ContentExceptionEnum implements ServiceExceptionEnum{

	NOT_CREATOR(900,"你不是作者无法修改和删除");

	ContentExceptionEnum(int code, String message) {
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

