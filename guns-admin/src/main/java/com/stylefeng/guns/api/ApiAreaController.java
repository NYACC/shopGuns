package com.stylefeng.guns.api;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.delivery.service.IAreaService;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 地区接口
 * @author 54476
 *
 */

@Controller
@RequestMapping("/area")
public class ApiAreaController extends BaseController{

	@Resource
	IAreaService areaService;
	
	@ApiOperation(value = "获取省市列表", notes = "获取省市列表")
	@RequestMapping(value = "/getAreaList",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAreaList(@ApiParam("pid") @RequestParam(value = "pid", required = true)@RequestBody long pid) {
		JSONObject jb = new JSONObject();
		if(pid == 0) {
			jb.put("province", areaService.getProvince());
		}else {
			jb.put("city", areaService.getCity(pid));
		}
		return jb;
	}
}
