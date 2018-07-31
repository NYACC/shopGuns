package com.md.goods.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.BrandMapper;
import com.md.goods.model.Brand;
import com.md.goods.service.IBrandService;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService  {

	@Autowired
	BrandMapper brandMapper;
	@Override
	public List<Map<String, Object>> find(Brand brand) {
		Wrapper<Brand> wrapper=new EntityWrapper<>();
		if(brand!=null&&brand.getName()!=null){
			wrapper.like("name", brand.getName());
		}	
		wrapper.orderBy("num", true);
		return this.brandMapper.selectMaps(wrapper);
		
	}

	@Override
	public boolean NameExist(String name) {
		Wrapper<Brand> wrapper=new EntityWrapper<>();
		wrapper.eq("name", name);
		List<Brand> searchList=this.brandMapper.selectList(wrapper);
		if(searchList!=null&&searchList.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public Brand findById(Long brandId) {
		return this.brandMapper.selectById(brandId);
	}


	@Override
	public Object update(Brand brand) {
		return this.brandMapper.updateById(brand);
	}

	@Override
	public Object deleteById(Long brandId) {
		return this.brandMapper.deleteById(brandId);
	}
	
	
}
