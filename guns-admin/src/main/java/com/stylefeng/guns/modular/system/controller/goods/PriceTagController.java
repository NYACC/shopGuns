package com.stylefeng.guns.modular.system.controller.goods;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.md.goods.model.PriceTag;
import com.md.goods.model.Product;
import com.md.goods.model.Shop;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IProductService;
import com.md.goods.service.IShopService;
import com.md.goods.warpper.PriceTagWarpper;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 价格标签控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/priceTag")
public class PriceTagController extends BaseController {

	private static String PREFIX = "/goods/goods/";
	@Resource
	IProductService productService;
	@Resource
	IPriceTagService priceTagService;
	@Resource
	IShopService shopService;

	/**
	 * 跳转到价格配置
	 */
	@RequestMapping(value = "/priceTag_assign/{goodsId}")
	public String priceTagAssign(@PathVariable("goodsId") Long goodsId, Model model) {
		//获取当前用户所在部门对应的门店
		Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());

		if (ToolUtil.isEmpty(goodsId)) {
			throw new GunsException(BizExceptionEnum.REQUEST_NULL);
		}
		// 获取所有的规格
		Product product = new Product();
		product.setGoodsId(goodsId);
		List<Map<String, Object>> productList = productService.find(product);

		// 去出所有对应的价格标签
		List<Map<String, Object>> priceTagList = priceTagService.findByGoodsId(goodsId, shopId);
		model.addAttribute("productList", JSONUtils.toJSONString(productList));
		model.addAttribute("priceTagList", JSONUtils.toJSONString(super.warpObject(new PriceTagWarpper(priceTagList))));
		return PREFIX + "priceTag_assign.html";
	}

	/**
	 * 提交价格标签修改
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(String priceTags) {
		String htmlUnescape = HtmlUtils.htmlUnescape(priceTags);
		List<PriceTag> priceTagList = JSONArray.parseArray(htmlUnescape, PriceTag.class);
		priceTagList.stream().forEach(p -> {
			priceTagService.edit(p);
		});
		return SUCCESS_TIP;
	}
}
