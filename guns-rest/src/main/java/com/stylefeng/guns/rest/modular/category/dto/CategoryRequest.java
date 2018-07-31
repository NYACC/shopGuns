package com.stylefeng.guns.rest.modular.category.dto;

/**
 * 
 * 类目请求
 * 
 * @author liuzhenghui
 *
 */
public class CategoryRequest {

	private Long pid;
	
	private Integer status;
	
	private Integer level;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
	
	
}
