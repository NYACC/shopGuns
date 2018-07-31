package com.md.goods.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.Shop;
import com.stylefeng.guns.core.node.ZTreeNode;

public interface IShopService extends IService<Shop> {
	/**
	 * 判断门店名是否存在
	 * @param stores
	 * @return
	 */
	boolean storesExist(Shop stores);

	/**
	 * 查询
	 * @param stores
	 * @return
	 */
	List<Map<String,Object>> find(Shop stores);

	/**
	 * 修改门店
	 * @param stores
	 */
	void edit(Shop stores);

	/**
	 * 根据编号删除门店
	 * @param storesId
	 */
	void delete(Long storesId);

	/**
	 * 根据编号查找门店
	 * @param storesId
	 * @return
	 */
	Shop findById(Long storesId);

	/**
	 * 获取门店列表树
	 * @return
	 */
	List<ZTreeNode> tree();
	
	/**
	 * 获取部门对应的门店Id
	 * @param deptId
	 * @return
	 */
	Long getShopIdByDeptId(Integer deptId);
}
