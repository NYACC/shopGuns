package com.md.goods.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.Category;
import com.md.goods.model.Goods;

/**
 * 商品服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
public interface IGoodsService extends IService<Goods> {
	/**
	 * 判断商品名称是否存在
	 * 
	 * @param goodsName
	 * @return
	 */
	boolean existGoods(String goodsName, Long goodsId);

	/**
	 * 查询
	 * 
	 * @param goods
	 * @return
	 */
	List<Map<String, Object>> find(Goods goods, String barcode);

	/**
	 * 根据编号查询商品
	 * 
	 * @param id
	 * @return
	 */
	Goods findById(Long id);

	/**
	 * 获取新的sn
	 * 
	 * @param
	 * @return
	 */
	String getNewSn();

	/**
	 * 添加商品
	 * 
	 * @param
	 * @return
	 */
	Long add(Goods goods);

	/**
	 * 修改商品
	 * 
	 * @param
	 * @return
	 */
	void edit(Goods goods);

	/**
	 * 判断规格规格项是被使用
	 * 
	 * @param
	 * @return
	 */
	Boolean specIsUse(Long id);

	/**
	 * 根据参数项的编号查找商品
	 * 
	 * @param ItemId
	 * @return
	 */
	List<Map<String, Object>> findGoodsByParamId(Long ItemId);

	/**
	 * 获取规格商品
	 * 
	 * @param shopIds
	 * @param categoryId
	 * @param brandId
	 * @param goodsName
	 */
	List<Map<String, Object>> goodsList(String shopIds, Long categoryId, Long brandId, String goodsName);

	/**
	 * 获取绑定的价格签
	 * 
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> findBindPriceTag(List<Long> ids);
	
	/**
	 *  通过类目id获取商品分页列表
	 * @param category 类目
	 * @param shopId 门店id
	 * @param index 当前页
	 * @return
	 */
	List<Map<String, Object>> getListByCate(Category category,Long shopId,Integer index);
	
	/**
	 *  通过商品标签id获取商品分页列表
	 * @param tag 商品标签
	 * @return
	 */
	List<Map<String, Object>> getListByTag(Long tagId,Long shopId,Integer index);
	
	/**
	 * 通过商品名称获取商品列表
	 * @param name
	 * @param shopId
	 * @param index
	 * @return
	 */
	List<Map<String, Object>> getListByName(String name,Long shopId,Integer index);

}
