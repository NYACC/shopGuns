package com.md.goods.exception;

import com.stylefeng.guns.core.exception.ServiceExceptionEnum;

/**
 * @Description 所有业务异常的枚举
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum GoodsExceptionEnum implements ServiceExceptionEnum{

	DELETE_BRAND(900,"品牌下存在商品,请先删除商品"),
	INSERT_BRAND(901,"品牌名称已存在"),
	REQUEST_NULL(400, "请求有错误"),

	DELETE_PARAM(900,"参数组下存在参数项，确定要将其删除"),
	NAME_SAME(501,"名称已存在"),
	SPEC_USED(502,"规格已被使用"),
	EXIST_GOODS(902,"存在商品,不能将其删除");
	

	GoodsExceptionEnum(int code, String message) {
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

