package com.md.member.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.member.model.Address;

public interface IAddressService extends IService<Address> {

	/**
	 * 查找用户的配送地址
	 * 
	 * @param memberId
	 * @return
	 */
	List<Map<String, Object>> findByMemberId(Long memberId);

	/**
	 * 添加
	 * 
	 * @param address
	 */
	void add(Address address);

	/**
	 * 修改
	 * 
	 * @param address
	 */
	void update(Address address);

	/**
	 * 删除
	 * 
	 * @param addressId
	 */
	void delete(Long addressId);

	/**
	 * 设置默认地址
	 * 
	 * @param addressId
	 */
	void setDefault(Long addressId);

	List<Map<String, Object>> myReceiver(Long memberId, boolean isdefault);
}
