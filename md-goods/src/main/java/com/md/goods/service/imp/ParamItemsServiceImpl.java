package com.md.goods.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.ParamItemsMapper;
import com.md.goods.model.Param;
import com.md.goods.model.ParamItems;
import com.md.goods.service.IParamItemsService;
import com.stylefeng.guns.core.util.ToolUtil;

@Transactional
@Service
public class ParamItemsServiceImpl extends ServiceImpl<ParamItemsMapper, ParamItems> implements IParamItemsService {
	@Resource
	ParamItemsMapper paramItemsMapper;

	@Override
	public ParamItems findByid(Long id) {
		return this.paramItemsMapper.selectById(id);
	}

	@Override
	public List<ParamItems> findParamItems(Param param) {
		if (ToolUtil.isEmpty(param.getItemId())) {
			return null;
		}
		String[] items = param.getItemId().split(",");
		List<ParamItems> itemList = new ArrayList<>();
		for (String item : items) {
			ParamItems itemObj = paramItemsMapper.selectById(Long.parseLong(item));
			itemList.add(itemObj);
		}
		return itemList;
	}

	@Override
	public void delete(Long ItemId) {
		paramItemsMapper.deleteById(ItemId);
	}

	@Override
	public ParamItems insert() {
		ParamItems paramItems = new ParamItems();
		paramItems.setNum(0);
		paramItemsMapper.insert(paramItems);
		return paramItems;
	}

	@Override
	public void edit(List<ParamItems> paramItems) {
		paramItems.stream().forEach(ParamItems -> {
			paramItemsMapper.updateById(ParamItems);
		});
	}
}
