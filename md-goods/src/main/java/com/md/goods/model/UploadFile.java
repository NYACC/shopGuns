package com.md.goods.model;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_upload_file")
public class UploadFile {

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String name;
	private String url;
	private Timestamp createTime;
	private Integer isUse;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Timestamp getCreateTime() {
		return createTime ;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	
	public UploadFile() {
		super();
	}

	public UploadFile(String url, Integer isUse, Timestamp createTime) {
		super();
		this.url=url;
		this.isUse=isUse;
		this.createTime=createTime;
	}
	
}
