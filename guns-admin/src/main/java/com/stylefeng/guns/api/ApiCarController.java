package com.stylefeng.guns.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.cart.constant.CartStatus;
import com.md.cart.model.Cart;
import com.md.cart.model.CartItem;
import com.md.cart.oe.CartObject;
import com.md.cart.oe.GoodObject;
import com.md.cart.oe.ShopObject;
import com.md.cart.service.ICartItemService;
import com.md.cart.service.ICartService;
import com.md.goods.model.Goods;
import com.md.goods.model.PriceTag;
import com.md.goods.model.Product;
import com.md.goods.model.UploadFile;
import com.md.goods.service.IGoodsService;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IProductService;
import com.md.goods.service.IShopService;
import com.md.goods.service.IUploadFileService;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/car")
public class ApiCarController extends BaseController {

	@Resource
	ICartService cartService;

	@Resource
	ICartItemService cartItemService;

	@Resource
	IPriceTagService priceTagService;

	@Resource
	IShopService shopService;

	@Resource
	IGoodsService goodsService;

	@Resource
	IProductService productService;
	
	@Resource
	IUploadFileService uploadFileService;

	@ApiOperation(value = "加入购物车", notes = "加入购物车")
	@RequestMapping(value = "/addCart", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addCart(
			@ApiParam("门店id") @RequestParam(value = "shopId", required = true) @RequestBody long shopId,
			@ApiParam("产品id") @RequestParam(value = "productId", required = true) @RequestBody long productId,
			@ApiParam("用户id") @RequestParam(value = "memberId", required = true) @RequestBody long memberId,
			@ApiParam("数量") @RequestParam(value = "quantity", required = true) @RequestBody Integer quantity) {
		JSONObject jb = new JSONObject();
		Cart cart = cartService.addSave(memberId); // 初始化购物车
		PriceTag tag = priceTagService.findByShopAndProduct(productId, shopId);
		CartItem cartItem = cartItemService.findByTagId(tag.getId(), cart.getId());
		if(cartItem != null) {
			cartItem.setQuantity(cartItem.getQuantity() + 1);
			cartItemService.updateById(cartItem); // 新增购物车项
		}else {
			CartItem cartItem2 = new CartItem();
			cartItem2.setCartId(cart.getId());
			cartItem2.setProductId(productId);
			cartItem2.setPriceTagId(tag.getId());
			cartItem2.setQuantity(quantity);
			cartItem2.setShopId(shopId);
			cartItem2.setStatus(CartStatus.YES.getCode());
			cartItemService.add(cartItem2); // 新增购物车项
			cart.setQuantity(cart.getQuantity() + 1);
			cartService.updateById(cart); // 加入购物车
		}
		jb.put("data", "加入购物车成功");
		return jb;
	}

	@ApiOperation(value = "修改购物车规格", notes = "修改购物车规格")
	@RequestMapping(value = "/modifyCart", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject modifyCart(
			@ApiParam("购物车项id") @RequestParam(value = "cartItemId", required = true) @RequestBody long cartItemId,
			@ApiParam("产品id") @RequestParam(value = "productId", required = true) @RequestBody long productId,
			@ApiParam("数量") @RequestParam(value = "quantity", required = true) @RequestBody Integer quantity) {
		JSONObject jb = new JSONObject();
		CartItem item = cartItemService.selectById(cartItemId);
		PriceTag tag = priceTagService.findByShopAndProduct(productId, item.getShopId());
		item.setPriceTagId(tag.getId());
		item.setProductId(productId);
		item.setQuantity(quantity);
		cartItemService.updateById(item);
		jb.put("data", "购物车修改成功");
		return jb;
	}

	@ApiOperation(value = "修改购物车数量", notes = "修改购物车数量")
	@RequestMapping(value = "/modifyCartQuan", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject modifyCartQuan(
			@ApiParam("购物车项id") @RequestParam(value = "cartItemId", required = true) @RequestBody long cartItemId,
			@ApiParam("数量") @RequestParam(value = "quantity", required = true) @RequestBody Integer quantity) {
		JSONObject jb = new JSONObject();
		CartItem item = cartItemService.selectById(cartItemId);
		item.setQuantity(quantity);
		cartItemService.updateById(item);
		jb.put("data", "购物车数量成功");
		return jb;
	}

	@ApiOperation(value = "购物车列表", notes = "购物车列表")
	@RequestMapping(value = "/getCartList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getCartList(
			@ApiParam("用户id") @RequestParam(value = "memberId", required = true) @RequestBody long memberId) {
		JSONObject jb = new JSONObject();
		CartObject cartOb = new CartObject();
		List<ShopObject> shopList = new ArrayList<>();
		Cart cart = cartService.findById(memberId);
		if (cart != null) {
			List<Long> shopResult = cartItemService.getShopListByCartId(cart.getId());
			if (shopResult.size() > 0) {
				for (int i = 0; i < shopResult.size(); i++) {
					List<GoodObject> goodList = new ArrayList<>();
					ShopObject shopItems = new ShopObject();
					shopItems.setShopid(shopResult.get(i));
					shopItems.setShopName(shopService.findById(shopResult.get(i)).getName());
					List<CartItem> itemResult = cartItemService.findByCartShop(cart.getId(), shopResult.get(i));
					if (itemResult.size() > 0) {
						for (CartItem item : itemResult) {
							GoodObject goodItems = new GoodObject();
							PriceTag tag = priceTagService.findByShopAndProduct(item.getProductId(), item.getShopId());
							goodItems.setCartItemId(item.getId());
							goodItems.setPrice(tag.getPrice());
							goodItems.setGoodsId(tag.getGoodsId());
							Goods goods = goodsService.findById(tag.getGoodsId());
							if(goods != null) {
								goodItems.setGoodsName(goods.getName());
							}
							Product product = productService.findById(item.getProductId());
							if(product != null) {
								goodItems.setSkuName(product.getName());
							}
							goodItems.setProductId(item.getProductId());
							goodItems.setQuantity(item.getQuantity());
							goodItems.setStatus(item.getStatus());
							UploadFile upload =  uploadFileService.getById(product.getImage());
							if(upload != null) {
								goodItems.setImageUrl(upload.getUrl());
							}
							goodList.add(goodItems);
						}
					}
					shopItems.setGoodItems(goodList);
					shopList.add(shopItems);
				}
				cartOb.setShopItems(shopList);
				cartOb.setCartId(cart.getId());
				cartOb.setMemberId(cart.getMemberId());
				cartOb.setQuantity(cart.getQuantity());
				
				jb.put("data", cartOb);
				jb.put("code", "success");
				return jb;
			} else {
				jb.put("data", cart);
				jb.put("code", "error");
				return jb;
			}
		} else {
			jb.put("data", cart);
			jb.put("code", "error");
			return jb;
		}
	}
	
	@ApiOperation(value = "删除购物车", notes = "删除购物车")
	@RequestMapping(value = "/deleteCar", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteCar(
			@ApiParam("购物车itemids") @RequestParam(value = "itemIds", required = true) @RequestBody String itemIds,
			@ApiParam("购物车cardId") @RequestParam(value = "cartId", required = true) @RequestBody Long cardId) {
		JSONObject jb = new JSONObject();
		String[] arr = itemIds.split(",");
		for(int i = 0; i < arr.length; i++) {
			cartItemService.deleteById(Long.valueOf(arr[i]));
		}
		Cart cart = cartService.selectById(cardId);
		cart.setQuantity(cart.getQuantity() - arr.length);
		cartService.updateById(cart);
		jb.put("data", "success");
		return jb;
	}
}
