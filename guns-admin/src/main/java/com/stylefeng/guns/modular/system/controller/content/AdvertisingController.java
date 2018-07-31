package com.stylefeng.guns.modular.system.controller.content;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.md.content.model.Advertising;
import com.md.content.service.IAdsPositionService;
import com.md.content.service.IAdvertisingService;
import com.md.content.warpper.AdvertisingWarpper;
import com.md.goods.model.Brand;
import com.md.goods.model.Shop;
import com.md.goods.service.IBrandService;
import com.md.goods.service.IGoodsService;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IShopService;
import com.md.goods.warpper.PriceTagWarpper;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.CompressUtil;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 广告controller
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/advertising")
public class AdvertisingController extends BaseController {

	@Resource
	IAdsPositionService adsPositionService;
	@Resource
	IAdvertisingService advertisingService;
	@Resource
	private GunsProperties gunsProperties;
	@Resource
	IShopService shopService;
	@Resource
	IBrandService brandService;
	@Resource
	IGoodsService goodsService;
	@Resource
	IPriceTagService priceTagService;

	private String PREFIX = "/content/advertising/";

	@RequestMapping("")
	public String index(Model model) {
		List<Map<String, Object>> positions = adsPositionService.list();
		model.addAttribute("positions", positions);
		return PREFIX + "list.html";
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAdd")
	public String toAdd(Model model) {
		List<Map<String, Object>> positions = adsPositionService.list();
		model.addAttribute("positions", positions);
		Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
		if (ToolUtil.isNotEmpty(shopId)) {
			Shop shop = shopService.findById(shopId);
			model.addAttribute("shop", shop);
		} else {
			List<Shop> shops = shopService.selectList(new EntityWrapper<Shop>());
			model.addAttribute("shops", shops);
			model.addAttribute("shop", null);
		}
		return PREFIX + "add.html";
	}

	/**
	 * 获取列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(Advertising advertising) {
		List<Map<String, Object>> advertisings = advertisingService.findList(advertising);
		return new AdvertisingWarpper(advertisings).warp();
	}

	/**
	 * 上传图片(上传到项目的webapp/static/img)
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/upload")
	@ResponseBody
	public String upload(@RequestPart("file") MultipartFile picture, Integer type) {
		// 保存文件
		String filename = picture.getOriginalFilename();
		String[] name = filename.split("\\.");
		String suffix = name[name.length - 1];
		String pictureName = UUID.randomUUID().toString() + "." + suffix;
		try {
			String fileSavePath = gunsProperties.getFileUploadPath();
			File f = new File(fileSavePath + "/advertisingImg");
			if (!f.exists()) {
				f.mkdirs();// 创建目录
			}
			File tempFile = new File(fileSavePath + pictureName);
			picture.transferTo(tempFile);
			// 压缩图片
			if (type == 1) {
				//轮播图
				CompressUtil.reduceImg(fileSavePath + pictureName, fileSavePath + "advertisingImg/" + pictureName, 320,
						117, null);
			}
			if (type == 2) {
				//正方形广告
				CompressUtil.reduceImg(fileSavePath + pictureName, fileSavePath + "advertisingImg/" + pictureName, 160,
						117, null);
			}
			if (type == 3) {
				//长方形广告
				CompressUtil.reduceImg(fileSavePath + pictureName, fileSavePath + "advertisingImg/" + pictureName, 320,
						80, null);
			}
			tempFile.delete();
		} catch (Exception e) {
			throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
		}
		return "advertisingImg/" + pictureName;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(Advertising advertising) {
		advertising.setCreateTime(new Timestamp(new Date().getTime()));
		advertisingService.add(advertising);
		return SUCCESS_TIP;
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	@RequestMapping("toEdit/{advertisingId}")
	public String toEdit(@PathVariable Long advertisingId, Model model) {
		List<Map<String, Object>> positions = adsPositionService.list();
		model.addAttribute("positions", positions);
		Advertising advertising = advertisingService.getById(advertisingId);
		model.addAttribute("advertising", advertising);
		Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
		if (ToolUtil.isNotEmpty(shopId)) {
			Shop shop = shopService.findById(shopId);
			model.addAttribute("shop", shop);
		} else {
			List<Shop> shops = shopService.selectList(new EntityWrapper<Shop>());
			model.addAttribute("shops", shops);
			model.addAttribute("shop", null);
		}
		return PREFIX + "edit.html";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(Advertising advertising) {
		advertisingService.update(advertising);
		return SUCCESS_TIP;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(Long advertisingId) {
		advertisingService.deleteById(advertisingId);
		return SUCCESS_TIP;
	}

	/**
	 * 跳转关联页面
	 * 
	 * @return
	 */
	@RequestMapping("toBind/{advertisingId}")
	public String toBind(@PathVariable Long advertisingId, Model model) {
		List<Map<String, Object>> brands = brandService.find(new Brand());
		model.addAttribute("brands", brands);
		model.addAttribute("promotionId", advertisingId);
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
	public Object goodsList(Long advertisingId, String shopIds, Long categoryId, Long brandId, String goodsName) {
		if (shopIds == null) {
			return null;
		}
		List<Map<String, Object>> goodsList = goodsService.goodsList(shopIds, categoryId, brandId, goodsName);
		// 获取当前的已绑定的商品价格标签
		Set<Long> addTags = new HashSet<>();
		Advertising advertising = advertisingService.getById(advertisingId);
		if (ToolUtil.isNotEmpty(advertising.getPriceTagIds())) {
			for (Long tagId : Convert.toLongArray(true, Convert.toStrArray(",", advertising.getPriceTagIds()))) {
				addTags.add(tagId);
			}
		}
		// 移除已绑定的价格标签
		List<Map<String, Object>> newGoodsList = new ArrayList<>();
		for (Map<String, Object> map : goodsList) {
			if (!addTags.contains(map.get("id"))) {
				newGoodsList.add(map);
			}
		}
		return newGoodsList;
	}

	/**
	 * 获取绑定的商品
	 */
	@RequestMapping(value = "/bindPriceTag/{advertisingId}")
	@ResponseBody
	public Object bindPriceTag(@PathVariable Long advertisingId) {
		Advertising advertising = advertisingService.getById(advertisingId);
		List<Long> ids = new ArrayList<>();
		if (ToolUtil.isNotEmpty(advertisingId)) {
			for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", advertising.getPriceTagIds()))) {
				ids.add(id);
			}
		}
		List<Map<String, Object>> priceTags = goodsService.findBindPriceTag(ids);
		return new PriceTagWarpper(priceTags).warp();
	}

	/**
	 * 解除绑定
	 */
	@RequestMapping(value = "/unbind")
	@ResponseBody
	public Object unbind(Long priceTagId, Long advertisingId) {
		advertisingService.unbindPriceTag(advertisingId, priceTagId);
		return SUCCESS_TIP;
	}

	/**
	 * 绑定关联
	 */
	@RequestMapping(value = "/bind")
	@ResponseBody
	public Object bind(Long priceTagId, Long advertisingId) {
		advertisingService.bindPriceTag(advertisingId, priceTagId);
		return SUCCESS_TIP;
	}
}
