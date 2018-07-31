package com.md.goods.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.TagMapper;
import com.md.goods.exception.GoodsExceptionEnum;
import com.md.goods.model.Tag;
import com.md.goods.service.ITagService;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.ToolUtil;


@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

	@Resource
	TagMapper tagMapper;

	@Override
	public List<Tag> list(Tag tag) {
		Wrapper<Tag> wrapper = new EntityWrapper<>();
		if(ToolUtil.isNotEmpty(tag.getName())){
			wrapper.like("name", "%" + tag.getName() + "%");
		}
		wrapper.orderBy("sequence");
		List<Tag> list = tagMapper.selectList(wrapper);
		return list;
	}

	@Override
	public void add(Tag tag) {
		if (ToolUtil.isEmpty(tag.getName())|| ToolUtil.isEmpty(tag.getSequence())) {
			throw new GunsException(GoodsExceptionEnum.REQUEST_NULL);
		}
		tagMapper.insert(tag);
	}

	@Override
	public void delete(Long id) {
		tagMapper.deleteById(id);
	}

	@Override
	public void edit(Tag tag) {
		if (ToolUtil.isEmpty(tag.getName())|| ToolUtil.isEmpty(tag.getSequence())) {
			throw new GunsException(GoodsExceptionEnum.REQUEST_NULL);
		}
		tagMapper.updateById(tag);
	}

	@Override
	public Tag getById(Long id) {
		return tagMapper.selectById(id);
	}

	public boolean checkNameExist(Long id, String name) {
		Wrapper<Tag> wrapper = new EntityWrapper<Tag>();
		wrapper.eq("name", name);
		List<Tag> selectList = tagMapper.selectList(wrapper);
		if (selectList.size() > 0) {
			if (!selectList.get(0).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

}
