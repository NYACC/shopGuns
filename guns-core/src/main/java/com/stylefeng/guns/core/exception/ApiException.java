package com.stylefeng.guns.core.exception;

public class ApiException {
	private Integer code;

	private String message;

	public ApiException(ServiceExceptionEnum serviceExceptionEnum) {
	        this.code = serviceExceptionEnum.getCode();
	        this.message = serviceExceptionEnum.getMessage();
	    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
