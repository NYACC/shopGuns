package com.md.goods.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.Specification;
import com.md.goods.model.SpecificationItem;

/**
 * 规格组service
 *
 */
public interface ISpecificationService extends IService<Specification> {

	/**
	 * 验证分类下是否有该规格
	 * 
	 * @param categoryId
	 * @param id
	 * @param name
	 * @return
	 */
	boolean checkNameOnCategory(Long categoryId, Long id, String name);

	/**
	 * 查找规格列表
	 * 
	 * @param queryObj
	 * @return
	 */
	List<Map<String, Object>> findList(Specification queryObj);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改
	 * 
	 * @param specification
	 */
	void edit(Specification specification);

	/**
	 * 通过id获取规格
	 * 
	 * @param id
	 * @return
	 */
	Specification getById(Long id);

	/**
	 * 添加规格
	 * 
	 * @param specification
	 * @param specificationItems
	 */
	void add(Specification specification, Set<SpecificationItem> specificationItems);

	/**
	 * 通过分类查找规格
	 * 
	 * @param cid
	 * @return
	 */
	List<Specification> findByCid(Long cid);
}
