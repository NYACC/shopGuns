package com.stylefeng.guns.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.goods.model.Category;
import com.md.goods.model.Goods;
import com.md.goods.model.PriceTag;
import com.md.goods.model.Product;
import com.md.goods.model.UploadFile;
import com.md.goods.oe.GoodsObject;
import com.md.goods.oe.ProductObject;
import com.md.goods.service.IBrandService;
import com.md.goods.service.ICategoryRelationService;
import com.md.goods.service.ICategoryService;
import com.md.goods.service.IGoodsService;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IProductService;
import com.md.goods.service.IShopService;
import com.md.goods.service.IUploadFileService;
import com.md.goods.warpper.GoodsWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 商品接口
 * 
 * @author 54476
 *
 */
@Controller
@RequestMapping("/good")
public class ApiGoodsController extends BaseController {

	@Resource
	ICategoryService categoryService;

	@Resource
	ICategoryRelationService categoryRelationService;

	@Resource
	IGoodsService goodsService;

	@Resource
	IProductService productService;

	@Resource
	IPriceTagService priceTagService;

	@Resource
	IUploadFileService uploadFileService;

	@Resource
	IBrandService brandService;

	@Resource
	IShopService shopService;

	@ApiOperation(value = "通过分类获取商品列表", notes = "通过分类获取商品列表")
	@RequestMapping(value = "/getListByCategory", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getListByCategory(
			@ApiParam("分类id") @RequestParam(value = "cateId", required = true) @RequestBody long cateId,
			@ApiParam("门店id") @RequestParam(value = "shopId", required = true) @RequestBody long shopId,
			@ApiParam("当前页") @RequestParam(value = "index", required = true) @RequestBody Integer index) {
		JSONObject jb = new JSONObject();
		Category category = categoryService.getById(cateId);
		List<Map<String, Object>> result = goodsService.getListByCate(category, shopId, index);
		jb.put("data", super.warpObject(new GoodsWarpper(result)));
		jb.put("index", index);
		return jb;
	}
	
	@ApiOperation(value = "通过标签获取商品列表", notes = "通过标签获取商品列表")
	@RequestMapping(value = "/getListByTag", method = RequestMethod.POST)
	public ResponseEntity<?> getListByTag(@ApiParam("商品标签id") @RequestParam(value = "tagId", required = true) @RequestBody long tagId,
			@ApiParam("门店id") @RequestParam(value = "shopId", required = true) @RequestBody long shopId,
			@ApiParam("当前页") @RequestParam(value = "index", required = true) @RequestBody Integer index) {
		JSONObject jb = new JSONObject();
		List<Map<String, Object>> result = goodsService.getListByTag(tagId, shopId, index);
		jb.put("data", super.warpObject(new GoodsWarpper(result)));
		jb.put("index", index);
		return ResponseEntity.ok(jb);
	}

	@ApiOperation(value = "通过商品名称获取商品列表", notes = "通过商品名称获取商品列表")
	@RequestMapping(value = "/getListByName", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getListByName(
			@ApiParam("商品名称") @RequestParam(value = "name", required = false) @RequestBody String name,
			@ApiParam("门店id") @RequestParam(value = "shopId", required = true) @RequestBody long shopId,
			@ApiParam("当前页") @RequestParam(value = "index", required = true) @RequestBody Integer index) {
		JSONObject jb = new JSONObject();
		List<Map<String, Object>> result = goodsService.getListByName(name, shopId, index);
		jb.put("data", super.warpObject(new GoodsWarpper(result)));
		jb.put("index", index);
		return jb;
	}

	@ApiOperation(value = "获取商品详情", notes = "获取商品详情")
	@RequestMapping(value = "/getGoodDetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getGoodDetail(
			@ApiParam("商品id") @RequestParam(value = "goodsId", required = true) @RequestBody long goodsId,
			@ApiParam("门店id") @RequestParam(value = "shopId", required = true) @RequestBody long shopId) {
		JSONObject jb = new JSONObject();
		GoodsObject goodsOb = new GoodsObject();
		Goods goods = goodsService.findById(goodsId);
		String imageUrl = "";
		if (goods.getImages() != null && !goods.getImages().equals("")) {
			String[] arr = goods.getImages().split(",");
			for (int i = 0; i < arr.length; i++) {
				UploadFile uploadFile = uploadFileService.getById(Long.valueOf(arr[i]));
				if(uploadFile != null) {
					imageUrl += uploadFile.getUrl() + ",";
				}
			}
			goodsOb.setImages(imageUrl);
		}
		Long stock = priceTagService.getSumByStock(goodsId, shopId);
		goodsOb.setGoodName(goods.getName());
		goodsOb.setShopName(shopService.findById(shopId).getName());
		goodsOb.setBrandName(brandService.findById(goods.getBrandId()).getName());
		goodsOb.setId(goods.getId());
		goodsOb.setMarketPrice(goods.getMarketPrice());
		goodsOb.setPrice(goods.getPrice());
		goodsOb.setStock(stock);
		goodsOb.setUnit(goods.getUnit());

		jb.put("goods", goodsOb);

		return jb;
	}

	@ApiOperation(value = "获取商品规格列表", notes = "获取商品规格列表")
	@RequestMapping(value = "/getProductList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getProductList(
			@ApiParam("商品id") @RequestParam(value = "goodsId", required = true) @RequestBody long goodsId,
			@ApiParam("门店id") @RequestParam(value = "shopId", required = true) @RequestBody long shopId) {
		JSONObject jb = new JSONObject();
		Goods goods = goodsService.findById(goodsId);
		List<ProductObject> productObList = new ArrayList<>();
		List<PriceTag> priceTagList = priceTagService.getProdectListByGandS(goodsId, shopId);
		if(priceTagList.size() > 0) {
			for(PriceTag priceTag:priceTagList) {
				ProductObject productOb = new ProductObject();
				Product product = productService.findById(priceTag.getProductId());
				productOb.setId(priceTag.getProductId());
				productOb.setTagId(priceTag.getId());
				productOb.setMarketPrice(priceTag.getMarketPrice());
				productOb.setPrice(priceTag.getPrice());
				productOb.setStock(priceTag.getInventory());
				productOb.setSkuName(product.getName());
				UploadFile uploadFile = uploadFileService.getById(product.getImage());
				if(uploadFile != null) {
					productOb.setImage(uploadFile.getUrl());
				}
				productObList.add(productOb);
			}
		}
		jb.put("speList", JSONObject.parse(goods.getSpecItems()));
		jb.put("productList", productObList);
		return jb;
	}
	

	@ApiOperation(value = "获取产品详情", notes = "获取产品详情")
	@RequestMapping(value = "/getProductDetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getProductDetail(
			@ApiParam("价格标签id") @RequestParam(value = "tagId", required = true) @RequestBody Long priceTagId) {
		JSONObject jb = new JSONObject();
		PriceTag tag = priceTagService.selectById(priceTagId);
		ProductObject productObject = new ProductObject();
		if(tag != null) {
			Product product = productService.findById(tag.getProductId());
			Goods goods = goodsService.findById(tag.getGoodsId());
			productObject.setId(tag.getProductId());
			productObject.setMarketPrice(tag.getMarketPrice());
			productObject.setPrice(tag.getPrice());
			productObject.setSkuName(product.getName());
			productObject.setGoodsName(goods.getName());
			productObject.setWeight(product.getWeight());
			UploadFile upload = uploadFileService.getById(product.getImage());
			if(upload != null) {
				productObject.setImage(upload.getUrl());
			}
		}
		jb.put("product", productObject);
		return jb;
	}

}
