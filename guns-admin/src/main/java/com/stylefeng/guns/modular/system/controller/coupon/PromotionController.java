package com.stylefeng.guns.modular.system.controller.coupon;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.coupon.constant.CouponModel;
import com.md.coupon.constant.PromotionStatus;
import com.md.coupon.exception.CouponExceptionEnum;
import com.md.coupon.model.Coupon;
import com.md.coupon.model.Promotion;
import com.md.coupon.service.ICouponService;
import com.md.coupon.service.IPromotionPriceTagService;
import com.md.coupon.service.IPromotionService;
import com.md.coupon.warpper.PromotionWarpper;
import com.md.goods.model.Brand;
import com.md.goods.model.Shop;
import com.md.goods.service.IBrandService;
import com.md.goods.service.IGoodsService;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IProductService;
import com.md.goods.service.IShopService;
import com.md.goods.warpper.PriceTagWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 促销controller
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/promotion")
public class PromotionController extends BaseController {

	@Resource
	IPromotionService promotionService;
	@Resource
	IShopService shopService;
	@Resource
	IBrandService brandService;
	@Resource
	IGoodsService goodsService;
	@Resource
	IProductService productService;
	@Resource
	IPriceTagService priceTagService;
	@Resource
	IPromotionPriceTagService promotionPriceTagService;
	@Resource
	ICouponService couponService;

	private String PREFIX = "/coupon/promotion/";

	@RequestMapping("")
	public String index() {
		return PREFIX + "list.html";
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
		} else {
			model.addAttribute("shop", null);
		}
		// 获取领取类型的优惠卷列表
		Coupon coupon = new Coupon();
		coupon.setModel(CouponModel.GIVECOUPON.getCode());
		coupon.setShopId(shopId);
		model.addAttribute("coupons", couponService.find(coupon));
		return PREFIX + "add.html";
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	@RequestMapping("toEdit/{promotionId}")
	public String toEdit(@PathVariable Long promotionId, Model model) {
		Promotion promotion = promotionService.getById(promotionId);
		model.addAttribute("promotion", promotion);
		Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
		if (ToolUtil.isNotEmpty(shopId)) {
			Shop shop = shopService.findById(shopId);
			model.addAttribute("shop", shop);
		} else {
			model.addAttribute("shop", null);
		}
		// 获取领取类型的优惠卷列表
		Coupon coupon = new Coupon();
		coupon.setModel(CouponModel.GIVECOUPON.getCode());
		coupon.setShopId(shopId);
		model.addAttribute("coupons", couponService.find(coupon));
		return PREFIX + "edit.html";
	}

	/**
	 * 跳转关联页面
	 * 
	 * @return
	 */
	@RequestMapping("toBind/{promotionId}")
	public String toBind(@PathVariable Long promotionId, Model model) {
		List<Map<String, Object>> brands = brandService.find(new Brand());
		model.addAttribute("brands", brands);
		model.addAttribute("promotionId", promotionId);
		Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
		if (ToolUtil.isNotEmpty(shopId)) {
			Shop shop = shopService.findById(shopId);
			model.addAttribute("shop", shop);
		} else {
			model.addAttribute("shop", null);
		}
		return PREFIX + "bind.html";
	}

	/**
	 * 跳转到门店树
	 */
	@RequestMapping(value = "/shop_select")
	public String shop_select() {
		return PREFIX + "shopTree.html";
	}

	/**
	 * 获取促销列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(Promotion promotion) {
		List<Map<String, Object>> promotions = promotionService.find(promotion);
		return new PromotionWarpper(promotions).warp();
	}

	/**
	 * 添加促销
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(Promotion promotion, String shopIds) {
		promotion.setStatus(PromotionStatus.NOT_START.getCode());
		promotion.setCreateTime(new Timestamp(new Date().getTime()));
		for (Long shopId : Convert.toLongArray(true, Convert.toStrArray(",", shopIds))) {
			promotion.setId(null);
			promotion.setShopId(shopId);
			promotionService.add(promotion);
		}
		return SUCCESS_TIP;
	}

	/**
	 * 选择门店
	 */
	@RequestMapping("/setShop")
	@ResponseBody
	public Object setShop(@RequestParam("ids") String ids) {
		List<Shop> list = new ArrayList<>();
		for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", ids))) {
			list.add(shopService.findById(id));
		}
		return list;
	}

	/**
	 * 获得搜索的商品列表
	 */
	@RequestMapping(value = "/goodsList")
	@ResponseBody
	public Object goodsList(Long promotionId, String shopIds, Long categoryId, Long brandId, String goodsName) {
		if (ToolUtil.isEmpty(shopIds)) {
			shopIds = "0";
		}
		List<Map<String, Object>> goodsList = goodsService.goodsList(shopIds, categoryId, brandId, goodsName);
		// 去除这个事件段已参加促销的商品
		List<Map<String, Object>> newGoods = new ArrayList<>();
		// 获取当前促销
		Promotion promotion = promotionService.getById(promotionId);
		for (Map<String, Object> map : goodsList) {
			// 判断该商品是否允许关联
			if (promotionService.allowBind((Long) map.get("id"), promotion)) {
				newGoods.add(map);
			}
		}
		return newGoods;
	}

	/**
	 * 绑定关联
	 */
	@RequestMapping(value = "/bind")
	@ResponseBody
	public Object bind(Long promotionId, Long priceTagId) {
		promotionService.bindPriceTag(promotionId, priceTagId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(Promotion promotion) {
		if (!promotionService.isOperable(promotion.getId())) {
			throw new GunsException(CouponExceptionEnum.NOT_OPERABLE);
		}
		promotionService.update(promotion);
		return SUCCESS_TIP;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(Long promotionId) {
		if (!promotionService.isOperable(promotionId)) {
			throw new GunsException(CouponExceptionEnum.NOT_OPERABLE);
		}
		promotionService.deleteById(promotionId);
		promotionPriceTagService.deleteByPromotionId(promotionId);
		return SUCCESS_TIP;
	}

	/**
	 * 获取促销绑定的商品
	 */
	@RequestMapping(value = "/bindPriceTag/{promotionId}")
	@ResponseBody
	public Object bindPriceTag(@PathVariable Long promotionId) {
		List<Long> ids = promotionPriceTagService.findPriceTagIds(promotionId);
		List<Map<String, Object>> priceTags = goodsService.findBindPriceTag(ids);
		Object warp = new PriceTagWarpper(priceTags).warp();
		if (ToolUtil.isEmpty(warp)) {
			return new ArrayList<>();
		}
		return warp;
	}

	/**
	 * 解除绑定
	 */
	@RequestMapping(value = "/unbind")
	@ResponseBody
	public Object unbind(Long priceTagId, Long promotionId) {
		promotionService.unbindPriceTag(promotionId, priceTagId);
		return SUCCESS_TIP;
	}

	/**
	 * 停用
	 */
	@RequestMapping(value = "/disable")
	@ResponseBody
	public Object disable(Promotion promotion) {
		promotion.setStatus(PromotionStatus.DISABLE.getCode());
		promotionService.update(promotion);
		return SUCCESS_TIP;
	}
}
