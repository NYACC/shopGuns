package com.stylefeng.guns.member;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.md.member.dao.AddressMapper;
import com.md.member.model.Address;
import com.md.member.service.IAddressService;
import com.stylefeng.guns.base.BaseJunit;

public class addressTest extends BaseJunit {

	@Resource
	AddressMapper addressMapper;
	@Resource
	IAddressService addressService;

	Address address1;
	Address address2;
	Address address3;

	@Before
	public void init() {
		address1 = new Address();
		address1.setId(1L);
		address1.setIsdefault(true);
		address1.setMemberId(1L);
		addressMapper.insert(address1);

		address2 = new Address();
		address2.setId(2L);
		address2.setIsdefault(false);
		address2.setMemberId(1L);
		addressMapper.insert(address2);

		address3 = new Address();
		address3.setId(3L);
		address3.setIsdefault(false);
		address3.setMemberId(1L);
		addressMapper.insert(address3);
	}

	@After
	public void destroy() {
		addressMapper.deleteById(address1.getId());
		addressMapper.deleteById(address2.getId());
		addressMapper.deleteById(address3.getId());

		address1 = null;
		address2 = null;
		address3 = null;
	}

	// 将地址2设置为默认地址
	@Test
	public void setDefault_2() {
		addressService.setDefault(address2.getId());
		Boolean expected = true;
		Boolean actual = addressMapper.selectById(address2.getId()).getIsdefault();
		assertEquals(expected, actual);
		Boolean expected1 = false;
		Boolean actual1 = addressMapper.selectById(address1.getId()).getIsdefault();
		assertEquals(expected1, actual1);
	}

	// 将地址3设置为默认地址
	@Test
	public void setDefault_3() {
		addressService.setDefault(address3.getId());
		Boolean expected = true;
		Boolean actual = addressMapper.selectById(address3.getId()).getIsdefault();
		assertEquals(expected, actual);
		Boolean expected1 = false;
		Boolean actual1 = addressMapper.selectById(address1.getId()).getIsdefault();
		assertEquals(expected1, actual1);
	}
}
