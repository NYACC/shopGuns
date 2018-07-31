package com.md.goods.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.Tag;

/**
 * 标签service
 *
 */
public interface ITagService extends IService<Tag> {

	/**
	 * 查询标签列表
	 * 
	 * @param tag
	 * @return
	 */
	List<Tag> list(Tag tag);

	/**
	 * 添加标签
	 * 
	 * @param tag
	 */
	void add(Tag tag);

	/**
	 * 删除标签
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改标签
	 * 
	 * @param product
	 */
	void edit(Tag product);

	/**
	 * 通过id查找标签
	 * 
	 * @param id
	 * @return
	 */
	Tag getById(Long id);

	/**
	 * 验证标签是否存在
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean checkNameExist(Long id, String name);

}
