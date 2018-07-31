package com.md.goods.service.imp;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.constant.IsUse;
import com.md.goods.dao.UploadFileMapper;
import com.md.goods.model.UploadFile;
import com.md.goods.service.IUploadFileService;

@Service
@Transactional
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFile> implements IUploadFileService {
	@Resource
	UploadFileMapper uploadFileMapper;

	@Override
	public Long add(UploadFile uploadFile) {
		uploadFileMapper.insert(uploadFile);
		return uploadFile.getId();
	}

	@Override
	public void delete(Long id) {
		uploadFileMapper.deleteById(id);
	}

	@Override
	public void allUse(Long[] ids) {
		for (Long id : ids) {
			UploadFile selectById = uploadFileMapper.selectById(id);
			selectById.setIsUse(IsUse.USE.getCode());
			uploadFileMapper.updateById(selectById);
		}
	}

	@Override
	public UploadFile getById(Long id) {
		UploadFile selectById = uploadFileMapper.selectById(id);
		return selectById;
	}

	@Override
	public void allUse(Set<Long> ids) {
		for (Long id : ids) {
			UploadFile selectById = uploadFileMapper.selectById(id);
			selectById.setIsUse(IsUse.USE.getCode());
			uploadFileMapper.updateById(selectById);
		}
	}

	@Override
	public void unUse(Long id) {
		UploadFile selectById = uploadFileMapper.selectById(id);
		selectById.setIsUse(IsUse.NOTUSED.getCode());
		uploadFileMapper.updateById(selectById);
	}

	@Override
	public void allUnUse(Long[] ids) {
		for (Long id : ids) {
			this.unUse(id);
		}
	}

}
