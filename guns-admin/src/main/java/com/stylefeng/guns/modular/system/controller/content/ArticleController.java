package com.stylefeng.guns.modular.system.controller.content;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.md.content.exception.ContentExceptionEnum;
import com.md.content.factory.ArticleFactory;
import com.md.content.model.Article;
import com.md.content.service.IArticleService;
import com.md.goods.model.Shop;
import com.md.goods.service.IShopService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 文章controller
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

	@Resource
	IArticleService articleService;
	@Resource
	IShopService shopService;

	private String PREFIX = "/content/article/";

	@RequestMapping("")
	public String index() {
		return PREFIX + "list.html";
	}

	/**
	 * 跳转到门店树
	 */
	@RequestMapping(value = "/shop_select")
	public String shop_select() {
		return PREFIX + "shopTree.html";
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAdd")
	public String toAdd(Model model) {
		Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
		if (ToolUtil.isNotEmpty(shopId)) {
			Shop shop = shopService.findById(shopId);
			model.addAttribute("shop", shop);
		}else{
			model.addAttribute("shop", null);
		}
		return PREFIX + "add.html";
	}

	/**
	 * 获取列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(Article article) {
		List<Map<String, Object>> articles = articleService.findList(article);
		return articles;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(Article article) {
		article.setCreatorId(ShiroKit.getUser().getId().longValue());
		article.setCreator(ShiroKit.getUser().getName());
		article.setContent(HtmlUtils.htmlUnescape(article.getContent()));
		article.setCreateTime(new Timestamp(new Date().getTime()));
		articleService.add(article);
		return SUCCESS_TIP;
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	@RequestMapping("toEdit/{articleId}")
	public String toEdit(@PathVariable Long articleId, Model model) {
		Article article = articleService.getById(articleId);
		if (article.getCreatorId() == ShiroKit.getUser().getId().longValue()) {
			article.setShopIds(ArticleFactory.me().getShopIds(articleId));
			model.addAttribute("article", article);
			Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
			if (ToolUtil.isNotEmpty(shopId)) {
				Shop shop = shopService.findById(shopId);
				model.addAttribute("shop", shop);
				model.addAttribute("shopIds", null);
			}else{
				model.addAttribute("shop", null);
				model.addAttribute("shopIds", ArticleFactory.me().getShopIds(articleId));
			}
			return PREFIX + "edit.html";
		} else {
			throw new GunsException(ContentExceptionEnum.NOT_CREATOR);
		}
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(Article article) {
		articleService.update(article);
		return SUCCESS_TIP;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(Long articleId) {
		articleService.deleteById(articleId);
		return SUCCESS;
	}
	
	
}
