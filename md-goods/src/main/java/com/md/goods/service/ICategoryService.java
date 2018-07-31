package com.md.goods.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.Category;
import com.stylefeng.guns.core.node.ZTreeNode;

/**
 * 分类service
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
public interface ICategoryService extends IService<Category> {

	/**
	 * 获取分类的树
	 *
	 * @author
	 * @Date
	 */
	List<ZTreeNode> tree();

	/**
	 * 通过id获取分类对象
	 *
	 * @author
	 * @Date
	 */
	Category getById(Long id);

	/**
	 * 校验分类名称是否存在
	 *
	 * @author
	 * @Date
	 */
	Boolean checkNameExist(Long id, String checkName);

	/**
	 * 停用分类
	 *
	 * @author
	 * @Date
	 */
	void disable(Long id);

	/**
	 * 启用分类
	 *
	 * @author
	 * @Date
	 */
	void enable(Long id);

	/**
	 * 修改分类
	 *
	 * @author
	 * @Date
	 */
	void edit(Category category);

	/**
	 * 添加分类
	 *
	 * @author
	 * @Date
	 */
	void add(Category category);

	/**
	 * 获取分类的子分类
	 *
	 * @author
	 * @Date
	 */
	List<Category> findSonList(Long id);

	/**
	 * 获取分类的子分类包装类
	 *
	 * @author
	 * @Date
	 */
	List<Map<String, Object>> findSonMaps(Long id);
	
	/**
	 * 获取开启状态的分类的子分类
	 *
	 * @author
	 * @Date
	 */
	List<Category> findCategoryList(Long id);
}
