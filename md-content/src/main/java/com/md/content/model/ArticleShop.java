package com.md.content.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 文章和门店的中间表
 * 
 * @author Administrator
 *
 */
@TableName("shop_article_shop")
public class ArticleShop {
	/**
	 * 主键id创建映射
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	// 文章Id
	private Long articleId;
	// 门店id
	private Long shopId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public ArticleShop() {
		super();
	}

	public ArticleShop(Long articleId, Long shopId) {
		super();
		this.articleId = articleId;
		this.shopId = shopId;
	}
}