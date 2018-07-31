package com.md.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.Brand;

import java.util.List;
import java.util.Map;

public interface IBrandService extends IService<Brand> {
	/**
	 * 查询品牌信息
	 * 
	 * @param brand
	 */
	List<Map<String, Object>> find(Brand brand);

	/**
	 * 判断品牌名是否存在
	 * 
	 * @param name
	 * @return
	 */
	boolean NameExist(String name);

	/**
	 * 根据Id查找单个对象
	 * 
	 * @param brandId
	 * @return
	 */
	Brand findById(Long brandId);

	/**
	 * 修改品牌信息
	 * 
	 * @param brand
	 * @return
	 */
	Object update(Brand brand);

	/**
	 * 删除品牌
	 * 
	 * @param brandId
	 * @return
	 */
	Object deleteById(Long brandId);
}
