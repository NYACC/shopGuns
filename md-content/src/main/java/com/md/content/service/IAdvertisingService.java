package com.md.content.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.md.content.model.Advertising;

public interface IAdvertisingService extends IService<Advertising> {

	/**
	 * 获取广告列表
	 * 
	 * @param advertising
	 * @return
	 */
	List<Map<String, Object>> findList(Advertising advertising);

	/**
	 * 添加
	 * 
	 * @param advertising
	 */
	void add(Advertising advertising);

	/**
	 * 通过id获取广告
	 * 
	 * @param advertisingId
	 * @return
	 */
	Advertising getById(Long advertisingId);

	/**
	 * 修改
	 * 
	 * @param advertising
	 */
	void update(Advertising advertising);

	/**
	 * 删除
	 * 
	 * @param advertisingId
	 */
	void deleteById(Long advertisingId);

	/**
	 * 删除广告位下的所有广告
	 * 
	 * @param adsPositionId
	 */
	void deletByPosition(Long adsPositionId);

	/**
	 * 绑定关联
	 * 
	 * @param advertisingId
	 * @param priceTagId
	 */
	void bindPriceTag(Long advertisingId, Long priceTagId);

	/**
	 * 解除关联
	 * 
	 * @param advertisingId
	 * @param priceTagId
	 */
	void unbindPriceTag(Long advertisingId, Long priceTagId);

}
