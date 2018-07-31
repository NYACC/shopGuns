package com.md.member.model;


import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_card_level")
public class CardLevel {
	/**
	 * 主键id
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;

	//等级名称
	private String name;
	//折扣
	private BigDecimal discount;
	//积分比例
	private BigDecimal integralProp;
	//排序
	private Integer num;
	
	
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
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getIntegralProp() {
		return integralProp;
	}
	public void setIntegralProp(BigDecimal integralProp) {
		this.integralProp = integralProp;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

}
