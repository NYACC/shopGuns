package com.md.delivery.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.delivery.model.Area;

public interface IAreaService extends IService<Area> {

	/**
	 * 获取所有的区/县
	 * 
	 * @return
	 */
	List<Area> countyList();

	/**
	 * 获取省
	 */
	List<Area> getProvince();

	/**
	 * 获取省下面的所有市
	 * 
	 * @param cityId
	 * @return
	 */
	List<Area> getCity(Long province);

	/**
	 * 获取市下面的所有县区
	 * 
	 * @param city
	 * @return
	 */
	List<Area> getCounty(Long city);

	/**
	 * 获取三级联动选中的地区Id
	 * 
	 * @param province
	 * @param city
	 * @param county
	 * @return
	 */
	List<Long> findCountyIds(Long province, Long city, Long county);

	/**
	 * 获取所有的区
	 * 
	 * @return
	 */
	List<Map<String, Object>> getAllCounty();
	
	/**
	 * 通过地区名称获取地区id
	 * @param fullName
	 * @return
	 */
	Area getByFullName(String fullName);
}
