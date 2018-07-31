package com.md.goods.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.ShopMapper;
import com.md.goods.model.Shop;
import com.md.goods.service.IShopService;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
@Transactional
public class StoresServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
	@Resource
	private ShopMapper storesMapper;

	@Override
	public boolean storesExist(Shop stores) {
		Wrapper<Shop> wrapper = new EntityWrapper<>();
		wrapper.eq("name", stores.getName());
		wrapper.eq("address", stores.getAddress());
		List<Map<String, Object>> storesList = storesMapper.selectMaps(wrapper);
		if (ToolUtil.isNotEmpty(storesList)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> find(Shop stores) {
		Wrapper<Shop> wrapper = new EntityWrapper<>();
		wrapper.like("name", stores.getName());
		wrapper.orderBy("num", true);
		return storesMapper.selectMaps(wrapper);
	}

	@Override
	public void edit(Shop stores) {
		storesMapper.updateById(stores);
	}

	@Override
	public void delete(Long storesId) {
		storesMapper.deleteById(storesId);
	}

	@Override
	public Shop findById(Long storesId) {
		return storesMapper.selectById(storesId);
	}

	@Override
	public List<ZTreeNode> tree() {
		List<ZTreeNode> list = new ArrayList<ZTreeNode>();
		List<Shop> shops = storesMapper.selectList(null);
		for (Shop shop : shops) {
			ZTreeNode zTreeNode = new ZTreeNode();
			zTreeNode.setId(shop.getId());
			zTreeNode.setName(shop.getName());
			list.add(zTreeNode);
		}
		return list;
	}

	@Override
	public Long getShopIdByDeptId(Integer deptId) {
		EntityWrapper<Shop> wrapper = new EntityWrapper<>();
		wrapper.eq("deptId", deptId);
		List<Shop> shops = storesMapper.selectList(wrapper);
		if (shops.size() > 0) {
			return shops.get(0).getId();
		} else {
			return null;
		}
	}

}
