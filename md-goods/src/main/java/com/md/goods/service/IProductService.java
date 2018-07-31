package com.md.goods.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.Product;

/**
 * 规格的service
 *
 */
public interface IProductService extends IService<Product> {

	/**
	 * 添加规格集合
	 *
	 * @author
	 * @Date
	 */
	void add(Product product);

	/**
	 * 修改规格集合
	 *
	 * @author
	 * @Date
	 */
	void edit(Product product);

	/**
	 * 根据编号删除product
	 * 
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 查询产品
	 * 
	 * @param product
	 * @return
	 */
	List<Map<String, Object>> find(Product product);

	/**
	 * 根据产品编号查找对应的规格产品
	 * 
	 * @param id
	 * @return
	 */
	Product findById(Long id);

	/**
	 * 根据barcode获取规格商品集合
	 * 
	 * @param barcode
	 * @return
	 */
	List<Map<String, Object>> findByBarcode(String barcode);
}
