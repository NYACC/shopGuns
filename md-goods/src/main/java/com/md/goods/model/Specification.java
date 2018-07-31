package com.md.goods.model;

import java.util.Set;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_specification")
public class Specification {

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String name;;
	private Integer sequence;
	private Long categoryId;
	@TableField(exist=false)
	private Set<SpecificationItem> items;
	
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
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Set<SpecificationItem> getItems() {
		return items;
	}
	public void setItems(Set<SpecificationItem> items) {
		this.items = items;
	}
	
}
