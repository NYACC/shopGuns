package com.md.goods.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.PriceTag;

/**
 * 价格标签service
 *
 */
public interface IPriceTagService extends IService<PriceTag> {

	/**
	 * 商品上架
	 *
	 * @author
	 * @Date
	 */
	void upper(Long id);

	/**
	 * 商品下架
	 *
	 * @author
	 * @Date
	 */
	void lower(Long id);

	/**
	 * 添加价格标签
	 *
	 * @author
	 * @Date
	 */
	void add(PriceTag priceTag);

	/**
	 * 修改价格标签
	 *
	 * @author
	 * @Date
	 */
	void edit(PriceTag priceTag);

	/**
	 * 删除价格标签
	 *
	 * @author
	 * @Date
	 */
	void delete(Long id);

	/**
	 * 根据规格id删除价格标签
	 * 
	 * @param productId
	 */
	void deleteByProductId(Long productId);

	/**
	 * 根据门店编号删除价格标签
	 * 
	 * @param storeId
	 */
	void deleteByStoreId(Long storeId);

	/**
	 * 根据product的编号查找价格标签
	 * 
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> findByGoodsId(Long id,Long shopId);

	/**
	 * 根据门店编号和规格编号查找价格标签
	 * 
	 * @param priceTag
	 * @return
	 */
	PriceTag findOne(PriceTag priceTag);

	/**
	 * 根据product的编号查找价格标签
	 * 
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> findList(PriceTag priceTag);

	/**
	 * 增加库存
	 * 
	 * @param productId
	 * @param shopId
	 * @param amount
	 */
	void addInventory(Long productId, Long shopId, Integer amount);

	/**
	 * 减少库存
	 * 
	 * @param productId
	 * @param shopId
	 * @param amount
	 */
	void reduceInventory(Long productId, Long shopId, Integer amount);

	/**
	 * 根据shopId和productId查找指定价格签
	 * 
	 * @param productId
	 * @param shopId
	 * @return
	 */
	PriceTag findByShopAndProduct(Long productId, Long shopId);
	
	/**
	 * 获取当前门店所有商品的库存
	 * @param goodsId 商品id
	 * @param shopId 门店id
	 * @return
	 */
	Long getSumByStock(Long goodsId, Long shopId);
	
	/**
	 * 通过商品id和门店id获取产品列表
	 * @param goodsId 商品id
	 * @param shopId 门店id
	 * @return
	 */
	List<PriceTag> getProdectListByGandS(Long goodsId, Long shopId);

}
