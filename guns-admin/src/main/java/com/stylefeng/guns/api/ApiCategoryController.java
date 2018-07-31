package com.stylefeng.guns.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.goods.model.Category;
import com.md.goods.service.ICategoryRelationService;
import com.md.goods.service.ICategoryService;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 分类接口
 * @author 54476
 *
 */
@Controller
@RequestMapping("/category")
public class ApiCategoryController extends BaseController{

	@Resource
	ICategoryService categoryService;
	
	@Resource
	ICategoryRelationService categoryRelationService;
	
	@ApiOperation(value = "获取分类列表", notes = "获取分类列表")
	@RequestMapping(value = "/getCategoryList",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getCategoryList(@ApiParam("pid") @RequestParam(value = "pid", required = true)@RequestBody long pid) {
		JSONObject jb = new JSONObject();
		List<Category> result = categoryService.findCategoryList(pid);
		if(pid != 0 ) {
			for(Category category : result ) {
				List<Category> list = categoryService.findCategoryList(category.getId());
				category.setList(list);
			}
		}
		jb.put("data", result);
		return jb;
	}
}
