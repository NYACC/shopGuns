package com.md.content.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.content.model.Article;

public interface IArticleService extends IService<Article>{

	/**
	 * 获取列表
	 * @param article
	 * @return
	 */
	List<Map<String, Object>> findList(Article article);

	/**
	 * 添加
	 * @param article
	 */
	void add(Article article);

	/**
	 * 通过id找对象
	 * @param articleId
	 * @return
	 */
	Article getById(Long articleId);

	/**
	 * 修改
	 * @param article
	 */
	void update(Article article);
	
	/**
	 * 删除
	 * @param articleId
	 */
	void deleteById(Long articleId);
	/**
	 * 通过门店获取文章列表
	 * @param shopId 门店id
	 * @return
	 */
	List<Map<String, Object>> findListByShopId(Long shopId);
	
}
