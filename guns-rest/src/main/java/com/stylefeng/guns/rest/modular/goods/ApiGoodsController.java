package com.stylefeng.guns.rest.modular.goods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
import com.stylefeng.guns.rest.modular.goods.dto.GoodsRequest;

import io.swagger.annotations.ApiOperation;

/**
 * 商品接口
 * 
 * @author 54476
 *
 */
@RestController
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
	public ResponseEntity<?> getListByCategory(@RequestBody GoodsRequest goodsRequest) {
		Category category = categoryService.getById(goodsRequest.getCateId());
		List<Map<String, Object>> result = goodsService.getListByCate(category, goodsRequest.getShopId(), goodsRequest.getIndex());
		return ResponseEntity.ok(super.warpObject(new GoodsWarpper(result)));
	}
	
	@ApiOperation(value = "通过标签获取商品列表", notes = "通过标签获取商品列表")
	@RequestMapping(value = "/getListByTag", method = RequestMethod.POST)
	public ResponseEntity<?> getListByTag(@RequestBody GoodsRequest goodsRequest) {
		List<Map<String, Object>> result = goodsService.getListByTag(goodsRequest.getTagId(), goodsRequest.getShopId(), goodsRequest.getIndex());
		return ResponseEntity.ok(super.warpObject(new GoodsWarpper(result)));
	}


	@ApiOperation(value = "通过商品名称获取商品列表", notes = "通过商品名称获取商品列表")
	@RequestMapping(value = "/getListByName", method = RequestMethod.POST)
	public ResponseEntity<?> getListByName(@RequestBody GoodsRequest goodsRequest) {
		List<Map<String, Object>> result = goodsService.getListByName(goodsRequest.getName(), goodsRequest.getShopId(), goodsRequest.getIndex());
		return ResponseEntity.ok(super.warpObject(new GoodsWarpper(result)));
	}

	@ApiOperation(value = "获取商品详情", notes = "获取商品详情")
	@RequestMapping(value = "/getGoodDetail", method = RequestMethod.POST)
	public ResponseEntity<?> getGoodDetail(@RequestBody GoodsRequest goodsRequest) {
		GoodsObject goodsOb = new GoodsObject();
		Goods goods = goodsService.findById(goodsRequest.getGoodsId());
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
		Long stock = priceTagService.getSumByStock(goodsRequest.getGoodsId(), goodsRequest.getShopId());
		goodsOb.setGoodName(goods.getName());
		goodsOb.setShopName(shopService.findById(goodsRequest.getShopId()).getName());
		goodsOb.setBrandName(brandService.findById(goods.getBrandId()).getName());
		goodsOb.setId(goods.getId());
		goodsOb.setMarketPrice(goods.getMarketPrice());
		goodsOb.setPrice(goods.getPrice());
		goodsOb.setStock(stock);
		goodsOb.setUnit(goods.getUnit());
		return ResponseEntity.ok(goodsOb);
	}

	@ApiOperation(value = "获取商品规格列表", notes = "获取商品规格列表")
	@RequestMapping(value = "/getProductList", method = RequestMethod.POST)
	public ResponseEntity<?> getProductList(@RequestBody GoodsRequest goodsRequest) {
		JSONObject jb = new JSONObject();
		Goods goods = goodsService.findById(goodsRequest.getGoodsId());
		List<ProductObject> productObList = new ArrayList<>();
		List<PriceTag> priceTagList = priceTagService.getProdectListByGandS(goodsRequest.getGoodsId(), goodsRequest.getShopId());
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
		return ResponseEntity.ok(jb);
	}
	

	@ApiOperation(value = "获取产品详情", notes = "获取产品详情")
	@RequestMapping(value = "/getProductDetail", method = RequestMethod.POST)
	public ResponseEntity<?> getProductDetail(@RequestBody GoodsRequest goodsRequest) {
		PriceTag tag = priceTagService.selectById(goodsRequest.getPriceTagId());
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
		}
		return ResponseEntity.ok(productObject);
	}

}
