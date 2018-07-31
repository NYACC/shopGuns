package com.md.goods.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.Param;


public interface IParamService extends IService<Param> {
	/**
	 * 查看参数组下是否有参数项
	 * @param paramId
	 * @return
	 */
	boolean existItems(Long paramId);
	/**
	 * 同分类下参数组名是否已经存在
	 * @param categoryId
	 * @param paramName
	 * @return
	 */
	boolean existParamName(Long categoryId,String paramName);
	/**
	 * 同参数组下的参数项是否已经存在
	 * @param itemName
	 * @param paramId
	 * @return
	 */
	boolean existItemsName(String itemName,Long paramId);
	/**
	 * 根据参数组的编号查找参数组
	 * @param paramId
	 * @return
	 */
	Param findById(Long paramId);
	/**
	 * 修改参数组
	 * @param goodsParam
	 */
	void edit(Param goodsParam);
	/**
	 * 删除参数组
	 */
	void delete(Long paramId);
	/**
	 * 根据条件查找参数组map集合
	 * @param param
	 */
	List<Map<String, Object>> find(Param param);
	/**
	 * 根据cid查找参数组集合
	 * @param cid
	 */
	List<Param> findByCid(Long cid);
}
