package com.md.goods.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.ProductMapper;
import com.md.goods.model.Product;
import com.md.goods.service.IProductService;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

	@Resource
	ProductMapper productMapper;

	@Override
	public void add(Product product) {
		productMapper.insert(product);
	}

	@Override
	public void edit(Product product) {
		productMapper.updateById(product);
	}

	@Override
	public List<Map<String, Object>> find(Product product) {
		Wrapper<Product> wrapper = new EntityWrapper<>();
		wrapper.eq("goodsId", product.getGoodsId());
		return productMapper.selectMaps(wrapper);
	}

	@Override
	public Product findById(Long id) {
		return productMapper.selectById(id);
	}

	@Override
	public void deleteById(Long id) {
		productMapper.deleteById(id);
	}

	@Override
	public List<Map<String, Object>> findByBarcode(String barcode) {
		Wrapper<Product> wrapper = new EntityWrapper<>();
		wrapper.like("barcode", barcode);
		return productMapper.selectMaps(wrapper);
	}
}
