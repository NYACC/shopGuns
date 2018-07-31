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

import com.md.content.model.AdsPosition;
import com.md.content.service.IAdsPositionService;
import com.md.content.service.IAdvertisingService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;

/**
 * 广告controller
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/adsPosition")
public class AdsPositionController extends BaseController {

	@Resource
	IAdsPositionService adsPositionService;
	@Resource
	IAdvertisingService advertisingService;

	private String PREFIX = "/content/adsPosition/";

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
	public String toAdd() {
		return PREFIX + "add.html";
	}

	/**
	 * 获取列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list() {
		List<Map<String, Object>> adsPositions = adsPositionService.list();
		return adsPositions;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(AdsPosition adsPosition) {
		adsPosition.setCreateTime(new Timestamp(new Date().getTime()));
		adsPosition.setCreator(ShiroKit.getUser().getName());
		adsPositionService.add(adsPosition);
		return SUCCESS_TIP;
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	@RequestMapping("toEdit/{adsPositionId}")
	public String toEdit(@PathVariable Long adsPositionId, Model model) {
		AdsPosition adsPosition = adsPositionService.getById(adsPositionId);
		model.addAttribute("adsPosition", adsPosition);
		return PREFIX + "edit.html";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(AdsPosition adsPosition) {
		adsPositionService.update(adsPosition);
		return SUCCESS_TIP;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(Long adsPositionId) {
		adsPositionService.deleteById(adsPositionId);
		advertisingService.deletByPosition(adsPositionId);
		return SUCCESS_TIP;
	}
}
