package com.md.content.factory;

import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.md.content.dao.ArticleShopMapper;
import com.md.content.model.ArticleShop;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 文章工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class ArticleFactory {
	private ArticleShopMapper articleShopMapper = SpringContextHolder.getBean(ArticleShopMapper.class);

	public static ArticleFactory me() {
		return SpringContextHolder.getBean(ArticleFactory.class);
	}

	public String getShopIds(Long articleId) {
		Wrapper<ArticleShop> wrapper = new EntityWrapper<>();
		wrapper.eq("articleId", articleId);
		List<ArticleShop> selectList = articleShopMapper.selectList(wrapper);
		String ids = "";
		for (ArticleShop articleShop : selectList) {
			ids += articleShop.getShopId() + ",";
		}
		return ids == "" ? ids : ids.substring(0, ids.length() - 1);
	}

}
