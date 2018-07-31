package com.md.member.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.member.dao.AddressMapper;
import com.md.member.model.Address;
import com.md.member.service.IAddressService;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

	@Resource
	AddressMapper addressMapper;

	@Override
	public List<Map<String, Object>> findByMemberId(Long memberId) {
		Wrapper<Address> wrapper = new EntityWrapper<>();
		wrapper.eq("memberId", memberId);
		List<Map<String, Object>> selectMaps = addressMapper.selectMaps(wrapper);
		return selectMaps;
	}

	@Override
	public void add(Address address) {
		addressMapper.insert(address);
	}

	@Override
	public void update(Address address) {
		addressMapper.updateById(address);
	}

	@Override
	public void delete(Long addressId) {
		addressMapper.deleteById(addressId);
	}

	@Override
	public void setDefault(Long addressId) {
		Address address = addressMapper.selectById(addressId);
		// 将该用户的其他默认地址设为false
		Wrapper<Address> wrapper = new EntityWrapper<>();
		wrapper.eq("memberId", address.getMemberId());
		wrapper.eq("isdefault", true);
		List<Address> selectList = addressMapper.selectList(wrapper);
		if (selectList != null) {
			for (Address oldAddress : selectList) {
				oldAddress.setIsdefault(false);
				this.update(oldAddress);
			}
		}
		// 将该地址的是否是默认地址，设为true
		address.setIsdefault(true);
		this.update(address);
	}

	@Override
	public List<Map<String, Object>> myReceiver(Long memberId, boolean isdefault) {
		// TODO 自动生成的方法存根
		Wrapper<Address> wrapper = new EntityWrapper<>();
		wrapper.eq("id", memberId);
		if(isdefault){
			wrapper.eq("isdefault", isdefault);
		}	
		return addressMapper.selectMaps(wrapper);
	}

}
