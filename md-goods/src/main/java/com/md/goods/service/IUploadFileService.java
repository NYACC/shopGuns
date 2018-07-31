package com.md.goods.service;

import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.md.goods.model.UploadFile;

/**
 * 标签service
 *
 */
public interface IUploadFileService extends IService<UploadFile> {

	/**
	 * 添加并返回对象的id
	 * 
	 * @param
	 * @return
	 */
	Long add(UploadFile uploadFile);

	/**
	 * 删除
	 * 
	 * @param
	 * @return
	 */
	void delete(Long id);

	/**
	 * 修改上传图片状态为使用
	 * 
	 * @param
	 * @return
	 */
	void allUse(Long[] ids);

	/**
	 * 修改上传图片状态为使用
	 * 
	 * @param
	 * @return
	 */
	void allUse(Set<Long> ids);

	/**
	 * 根据id获取上传文件对象
	 * 
	 * @param
	 * @return
	 */
	UploadFile getById(Long id);

	/**
	 * 修改单个上传文件的状态为未使用
	 * 
	 * @param
	 * @return
	 */
	void unUse(Long id);

	/**
	 * 修改多个上传文件的状态为未使用
	 * 
	 * @param
	 * @return
	 */
	void allUnUse(Long[] ids);

}
