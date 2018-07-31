package com.md.member.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.member.model.Denomination;

public interface IDenominationService extends IService<Denomination> {

	/**
	 * 获取面额列表
	 * 
	 * @param member
	 * @return
	 */
	List<Map<String, Object>> list();

	/**
	 * 添加面额
	 * 
	 * @param denomination
	 */
	void add(Denomination denomination);

	/**
	 * 根据id查找面额对象
	 * 
	 * @param denominationId
	 * @return
	 */
	Denomination getById(Long id);

	/**
	 * 修改面额
	 * 
	 * @param denomination
	 */
	void update(Denomination denomination);

	/**
	 * 删除面额
	 * 
	 * @param denominationId
	 */
	void delete(Long denominationId);

}
