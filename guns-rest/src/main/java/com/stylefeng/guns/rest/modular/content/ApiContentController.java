package com.stylefeng.guns.rest.modular.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
import com.stylefeng.guns.rest.modular.content.dto.ContentRequest;

import io.swagger.annotations.ApiOperation;

/**
 * 内容接口
 * @author 54476
 *
 */
@RestController
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
	public ResponseEntity<?> getAdvPosList(@RequestBody ContentRequest contentRequest) {
		
		Advertising advertising = new Advertising();
		advertising.setPositionId(contentRequest.getPositionId());
		advertising.setShopId(contentRequest.getShopId());
		List<Map<String, Object>> adverList = advertisingService.findList(advertising);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> adv:adverList){
			Map<String, Object> temp=new HashMap<String, Object>();
			temp.put("url", adv.get("link"));
			temp.put("img", "http://localhost:8080/wmshop/kaptcha/"+adv.get("picture"));
			temp.put("title", adv.get("name"));
			result.add(temp);
		}
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 获取广告详情和广告关联商品列表
	 * @param advertising 广告实体 /广告主键id 
	 * @return
	 */
	@ApiOperation(value = "获取广告详情", notes = "获取广告详情")
	@RequestMapping(value = "/getAdvAndGood",method = RequestMethod.POST)
	public ResponseEntity<?> getAdvAndGood(@RequestBody ContentRequest contentRequest) {
		JSONObject jb = new JSONObject();
		Advertising adver =	advertisingService.getById(contentRequest.getAdvId());
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
		return ResponseEntity.ok(jb);
	}
	
	/**
	 * 获取文章列表
	 * @param shopId 门店id
	 * @return
	 */
	@ApiOperation(value = "获取文章列表", notes = "获取文章列表")
	@RequestMapping(value = "/getArticleList",method = RequestMethod.POST)
	public ResponseEntity<?> getArticleList(@RequestBody ContentRequest contentRequest) {
		
		JSONObject jb = new JSONObject();
		ArticleShop articleShop = new ArticleShop();
		articleShop.setShopId(contentRequest.getShopId());
		List<Map<String, Object>> articleList = articleService.findListByShopId(contentRequest.getShopId());
		jb.put("data", articleList);
		return ResponseEntity.ok(articleList);
	}
	/**
	 * 获取文章详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取文章详情", notes = "获取文章详情")
	@RequestMapping(value = "/getArticleDetail",method = RequestMethod.POST)
	public ResponseEntity<?> getActicleDetail(@RequestBody ContentRequest contentRequest) {
		Article article = articleService.getById(contentRequest.getArticleId());
		return ResponseEntity.ok(article);
	}

	@ApiOperation(value = "获取关于我们", notes = "获取关于我们")
	@RequestMapping(value = "/getAboutUs",method = RequestMethod.POST)
	public ResponseEntity<?> getAboutUs(@RequestBody ContentRequest contentRequest) {
		JSONObject jb = new JSONObject();
		AboutUs us = aboutUsService.selectById(contentRequest.getAboutId());
		jb.put("data", us);
		return ResponseEntity.ok(us);
	}
}
