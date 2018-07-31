package com.stylefeng.guns.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.md.content.model.AboutUs;
import com.md.content.model.Advertising;
import com.md.content.model.Article;
import com.md.content.model.ArticleShop;
import com.md.content.service.IAboutUsService;
import com.md.content.service.IAdvertisingService;
import com.md.content.service.IArticleService;
import com.md.goods.model.Goods;
import com.md.goods.model.PriceTag;
import com.md.goods.service.IGoodsService;
import com.md.goods.service.IPriceTagService;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 内容接口
 * @author 54476
 *
 */
@Controller
@RequestMapping("/content")
public class ApiContentController extends BaseController{
	
	@Resource
	IAdvertisingService advertisingService;

	@Resource
	IPriceTagService priceTagService;
	
	@Resource
	IArticleService articleService;
	
	@Resource
	IGoodsService goodsService;
	
	@Resource
	IAboutUsService aboutUsService;
	/**
	 * 获取广告列表
	 * @param advertising 广告实体 /广告关联广告位id必传 positionId
	 * @return
	 */
	@ApiOperation(value = "获取广告列表", notes = "获取广告列表")
	@RequestMapping(value = "/getAdvPosList",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAdvPosList(@ApiParam("广告位置id，必填") @RequestParam(value = "positionId", required = true) @RequestBody long positionId,
			@ApiParam("门店id，必填") @RequestParam(value = "shopId", required = true) @RequestBody long shopId) {
		
		JSONObject jb = new JSONObject();
		Advertising advertising = new Advertising();
		advertising.setPositionId(positionId);
		advertising.setShopId(shopId);
		List<Map<String, Object>> adverList = advertisingService.findList(advertising);
		jb.put("data", adverList);
		return jb;
	}
	
	/**
	 * 获取广告详情和广告关联商品列表
	 * @param advertising 广告实体 /广告主键id 
	 * @return
	 */
	@ApiOperation(value = "获取广告详情", notes = "获取广告详情")
	@RequestMapping(value = "/getAdvAndGood",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAdvAndGood(@ApiParam("广告id") @RequestParam(value = "advId", required = true)@RequestBody long advId) {
		JSONObject jb = new JSONObject();
		Advertising adver =	advertisingService.getById(advId);
		if(adver.getPriceTagIds() != null) {
			List<Goods> goodsLit = new ArrayList<>();
			String[] arr = adver.getPriceTagIds().split(",");
			for(int i = 0; i<arr.length; i++) {
				PriceTag priceTag = new PriceTag();
				priceTag.setId(Long.valueOf(arr[i]));
				Long goodsId = priceTagService.findOne(priceTag).getGoodsId();
				Goods goods = goodsService.findById(goodsId);
				goodsLit.add(goods);
			}
			jb.put("goodsList", goodsLit);
		}
		jb.put("advDetail", adver);
		return jb;
	}
	
	/**
	 * 获取文章列表
	 * @param shopId 门店id
	 * @return
	 */
	@ApiOperation(value = "获取文章列表", notes = "获取文章列表")
	@RequestMapping(value = "/getArticleList",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getArticleList(@ApiParam("门店id，必填") @RequestParam(value = "shopId", required = true) @RequestBody long shopId) {
		
		JSONObject jb = new JSONObject();
		ArticleShop articleShop = new ArticleShop();
		articleShop.setShopId(shopId);
		List<Map<String, Object>> articleList = articleService.findListByShopId(shopId);
		jb.put("data", articleList);
		return jb;
	}
	/**
	 * 获取文章详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取文章详情", notes = "获取文章详情")
	@RequestMapping(value = "/getArticleDetail",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getActicleDetail(@ApiParam("文章id") @RequestParam(value = "articleId",required = true) @RequestBody long articleId) {
		JSONObject jb = new JSONObject();
		Article article = articleService.getById(articleId);
		jb.put("article", article);
		return jb;
	}
	
	@ApiOperation(value = "获取关于我们", notes = "获取关于我们")
	@RequestMapping(value = "/getAboutUs",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAboutUs(@ApiParam("关于我们id") @RequestParam(value = "aboutId",required = true) @RequestBody long aboutId) {
		JSONObject jb = new JSONObject();
		AboutUs us = aboutUsService.selectById(aboutId);
		jb.put("data", us);
		return jb;
	}
}
