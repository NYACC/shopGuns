package com.md.content.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.content.model.AdsPosition;

public interface IAdsPositionService extends IService<AdsPosition> {

	/**
	 * 获取列表
	 * 
	 * @return
	 */
	List<Map<String, Object>> list();

	/**
	 * 添加
	 * 
	 * @param adsPosition
	 */
	void add(AdsPosition adsPosition);

	/**
	 * 通过id获取对象
	 * 
	 * @param adsPositionId
	 * @return
	 */
	AdsPosition getById(Long adsPositionId);

	/**
	 * 修改
	 * 
	 * @param adsPosition
	 */
	void update(AdsPosition adsPosition);

	/**
	 * 删除
	 * 
	 * @param adsPositionId
	 */
	void deleteById(Long adsPositionId);

}
