package com.md.goods.model;

import java.util.Set;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_param")
public class Param {
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String name;
	private String itemId;
	private Long categoryId;
	private Integer num;
	@TableField(exist=false)
	private Set<ParamItems> items;

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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Set<ParamItems> getItems() {
		return items;
	}

	public void setItems(Set<ParamItems> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name=" + name +
				", itemId=" + itemId +
				", categoryId=" + categoryId +
				"}";
	}
	
}
