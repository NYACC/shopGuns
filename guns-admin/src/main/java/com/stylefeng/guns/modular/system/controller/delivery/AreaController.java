package com.stylefeng.guns.modular.system.controller.delivery;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.delivery.model.Area;
import com.md.delivery.service.IAreaService;
import com.stylefeng.guns.core.base.controller.BaseController;

/**
 * 配送方法controller
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController {

	@Resource
	IAreaService areaService;

	/**
	 * 获取所有省
	 */
	@RequestMapping(value = "/province")
	@ResponseBody
	public Object province() {
		List<Area> province = areaService.getProvince();
		return province;
	}
	
	/**
	 * 获取省下面所有市
	 */
	@RequestMapping(value = "/city")
	@ResponseBody
	public Object city(Long province) {
		List<Area> citys = areaService.getCity(province);
		return citys;
	}
	
	/**
	 * 获取市下面所有县区
	 */
	@RequestMapping(value = "/county")
	@ResponseBody
	public Object county(Long city) {
		List<Area> citys = areaService.getCounty(city);
		return citys;
	}
}
