package com.stylefeng.guns.rest.modular.area;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.md.delivery.service.IAreaService;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 地区接口
 * @author 54476
 *
 */

@RestController
@RequestMapping("/area")
public class ApiAreaController extends BaseController{

	@Resource
	IAreaService areaService;
	
	@ApiOperation(value = "获取省市列表", notes = "获取省市列表")
	@RequestMapping(value = "/getAreaList",method = RequestMethod.POST)
	public ResponseEntity<?> getAreaList(@ApiParam("pid") @RequestParam(value = "pid", required = true)@RequestBody long pid) {
		if(pid == 0) {
			return ResponseEntity.ok(areaService.getProvince());
		}else {
			return ResponseEntity.ok(areaService.getCity(pid));
		}
	}
}
