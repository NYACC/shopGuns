package com.stylefeng.guns.rest.modular.content.dto;

/**
 * 內容，广告，文章，关于我们
 * @author 54476
 *
 */
public class ContentRequest {

	private Long positionId;
	
	private Long shopId;
	
	private Long advId;
	
	private Long articleId;
	
	private Long aboutId;

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getAdvId() {
		return advId;
	}

	public void setAdvId(Long advId) {
		this.advId = advId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getAboutId() {
		return aboutId;
	}

	public void setAboutId(Long aboutId) {
		this.aboutId = aboutId;
	}
	
	
}
