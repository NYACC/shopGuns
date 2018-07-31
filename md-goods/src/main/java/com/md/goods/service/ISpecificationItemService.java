package com.md.goods.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.SpecificationItem;

/**
 * 标签service
 *
 */
public interface ISpecificationItemService extends IService<SpecificationItem>{

	boolean checkNameOnSpecification(Long pid, Long id, String name);
	
	/**
     * 根据规格组id获取规格项
     * @param pid
     * @return
     */
	List<SpecificationItem> getByPid(Long pid);
	
	/**
     * 添加
     * @param 
     * @return
     */
	Long add(SpecificationItem specificationItem);
	
	/**
     * 删除
     * @param 
     * @return
     */
	void delete(Long id);
	
	/**
     * 修改
     * @param 
     * @return
     */
	void update(SpecificationItem specificationItem);

}
