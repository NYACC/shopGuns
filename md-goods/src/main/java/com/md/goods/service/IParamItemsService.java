package com.md.goods.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.Param;
import com.md.goods.model.ParamItems;

public interface IParamItemsService extends IService<ParamItems> {
	/**
	 * 根据Id查找参数项
	 * 
	 * @param id
	 * @return
	 */
	ParamItems findByid(Long id);

	/**
	 * 根据参数组获得下面所有参数项
	 * 
	 * @param param
	 * @return
	 */
	List<ParamItems> findParamItems(Param param);

	/**
	 * 根据参数项的编号删除参数项
	 * 
	 * @param ItemId
	 */
	void delete(Long ItemId);

	/**
	 * 增加一条空值的参数项
	 * 
	 * @return
	 */
	ParamItems insert();

	/**
	 * 修改参数项
	 * 
	 * @param paramItems
	 */
	void edit(List<ParamItems> paramItems);

}
