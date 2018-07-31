package com.stylefeng.guns.content;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.md.content.dao.ArticleMapper;
import com.md.content.model.Article;
import com.md.content.service.IArticleService;
import com.stylefeng.guns.base.BaseJunit;

public class ArticleTest extends BaseJunit {

	@Resource
	ArticleMapper articleMapper;
	@Resource
	IArticleService articleService;

	Article article1;
	Article article2;
	Article article3;
	Article queryObj;

	@Before
	public void init() {
		article1 = new Article();
		article1.setTitle("盛大开业来袭");
		articleMapper.insert(article1);

		article2 = new Article();
		article2.setTitle("活动大酬宾");
		articleMapper.insert(article2);

		article3 = new Article();
		article3.setTitle("盛夏活动");
		articleMapper.insert(article3);

		queryObj = new Article();
	}

	@After
	public void destroy() {
		articleMapper.deleteById(article1.getId());
		articleMapper.deleteById(article2.getId());
		articleMapper.deleteById(article3.getId());

		article1 = null;
		article2 = null;
		article3 = null;
		queryObj = null;
	}

	// 查找“开业”标题的文章测试
	@Test
	public void find_titleKaiYe() {
		queryObj.setTitle("开业");
		Integer expected = 1;
		Integer actual = articleService.findList(queryObj).size();
		assertEquals(expected, actual);
	}

	// 查找“活动”标题的文章测试
	@Test
	public void find_titleHuoDong() {
		queryObj.setTitle("活动");
		Integer expected = 2;
		Integer actual = articleService.findList(queryObj).size();
		assertEquals(expected, actual);
	}

}
