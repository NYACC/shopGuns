package com.md.member.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_denomination")
public class Denomination {
	/**
	 * 主键id
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	//面额值
	private Integer value;
	//充值倍数
	private BigDecimal multiple;
	//规则使用次数
	private Integer times;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public BigDecimal getMultiple() {
		return multiple;
	}
	public void setMultiple(BigDecimal multiple) {
		this.multiple = multiple;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
}
