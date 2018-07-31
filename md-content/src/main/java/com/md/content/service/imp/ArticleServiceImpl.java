package com.md.content.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.content.dao.ArticleMapper;
import com.md.content.dao.ArticleShopMapper;
import com.md.content.model.Article;
import com.md.content.model.ArticleShop;
import com.md.content.service.IArticleService;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

	@Resource
	ArticleMapper articleMappper;
	@Resource
	ArticleShopMapper articleShopMapper;

	@Override
	public List<Map<String, Object>> findList(Article article) {
		Wrapper<Article> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(article.getTitle())) {
			wrapper.like("title", article.getTitle());
		}
		wrapper.orderBy("createTime", false);
		List<Map<String, Object>> selectMaps = articleMappper.selectMaps(wrapper);
		return selectMaps;
	}

	@Override
	public void add(Article article) {
		// 添加文章
		articleMappper.insert(article);
		// 添加文章门店关联
		for (Long shopId : Convert.toLongArray(true, Convert.toStrArray(",", article.getShopIds()))) {
			ArticleShop articleShop = new ArticleShop(article.getId(), shopId);
			articleShopMapper.insert(articleShop);
		}
	}

	@Override
	public Article getById(Long articleId) {
		return articleMappper.selectById(articleId);
	}

	@Override
	public void update(Article article) {
		// 删除门店和文章关联
		Wrapper<ArticleShop> wrapper = new EntityWrapper<>();
		wrapper.eq("articleId", article.getId());
		articleShopMapper.delete(wrapper);
		// 添加文章门店关联
		for (Long shopId : Convert.toLongArray(true, Convert.toStrArray(",", article.getShopIds()))) {
			ArticleShop articleShop = new ArticleShop(article.getId(), shopId);
			articleShopMapper.insert(articleShop);
		}
		articleMappper.updateById(article);
	}

	@Override
	public void deleteById(Long articleId) {
		// 删除门店和文章关联
		Wrapper<ArticleShop> wrapper = new EntityWrapper<>();
		wrapper.eq("articleId", articleId);
		articleShopMapper.delete(wrapper);
		// 删除文章
		articleMappper.deleteById(articleId);
	}

	@Override
	public List<Map<String, Object>> findListByShopId(Long shopId) {
		Wrapper<Article> wrapper = new EntityWrapper<>();
		wrapper.where("id in (select articleId from shop_article_shop where shopId = " + shopId + ")");
		return articleMappper.selectMaps(wrapper);
	}

}
