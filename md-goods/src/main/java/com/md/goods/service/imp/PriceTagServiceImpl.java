package com.md.goods.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.constant.Marketable;
import com.md.goods.dao.PriceTagMapper;
import com.md.goods.model.PriceTag;
import com.md.goods.service.IPriceTagService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class PriceTagServiceImpl extends ServiceImpl<PriceTagMapper, PriceTag> implements IPriceTagService {

	@Resource
	PriceTagMapper priceTagMapper;

	@Override
	public void upper(Long id) {
		PriceTag selectById = priceTagMapper.selectById(id);
		selectById.setMarketable(Marketable.UPPER.getCode());
		priceTagMapper.updateById(selectById);
	}

	@Override
	public void lower(Long id) {
		PriceTag selectById = priceTagMapper.selectById(id);
		selectById.setMarketable(Marketable.LOWER.getCode());
		priceTagMapper.updateById(selectById);
	}

	@Override
	public void add(PriceTag priceTag) {
		priceTagMapper.insert(priceTag);
	}

	@Override
	public void edit(PriceTag priceTag) {
		priceTagMapper.updateById(priceTag);
	}

	@Override
	public void delete(Long id) {
		priceTagMapper.deleteById(id);
	}

	@Override
	public void deleteByProductId(Long productId) {
		Wrapper<PriceTag> wrapper = new EntityWrapper<>();
		wrapper.eq("productId", productId);
		priceTagMapper.delete(wrapper);
	}

	@Override
	public void deleteByStoreId(Long storeId) {
		Wrapper<PriceTag> wrapper = new EntityWrapper<>();
		wrapper.eq("shopId", storeId);
		priceTagMapper.delete(wrapper);
	}

	@Override
	public List<Map<String, Object>> findByGoodsId(Long id,Long shopId) {
		Wrapper<PriceTag> wrapper = new EntityWrapper<>();
		wrapper.eq("goodsId", id);
		if (ToolUtil.isNotEmpty(shopId)) {
			wrapper.eq("shopId", shopId);
		}
		return priceTagMapper.selectMaps(wrapper);
	}

	@Override
	public PriceTag findOne(PriceTag priceTag) {
		return priceTagMapper.selectOne(priceTag);
	}

	@Override
	public List<Map<String, Object>> findList(PriceTag priceTag) {
		Wrapper<PriceTag> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(priceTag.getProductId())) {
			wrapper.eq("productId", priceTag.getProductId());
		}
		if (ToolUtil.isNotEmpty(priceTag.getShopId())) {
			wrapper.eq("shopId", priceTag.getShopId());
		}
		return priceTagMapper.selectMaps(wrapper);
	}

	@Override
	public void addInventory(Long productId, Long shopId, Integer amount) {
		PriceTag priceTag = new PriceTag();
		priceTag.setProductId(productId);
		priceTag.setShopId(shopId);
		PriceTag tag = findOne(priceTag);
		tag.setInventory(tag.getInventory() + amount);
		edit(tag);
	}

	@Override
	public void reduceInventory(Long productId, Long shopId, Integer amount) {
		PriceTag priceTag = new PriceTag();
		priceTag.setProductId(productId);
		priceTag.setShopId(shopId);
		PriceTag tag = findOne(priceTag);
		tag.setInventory(tag.getInventory() - amount);
		edit(tag);
	}

	@Override
	public PriceTag findByShopAndProduct(Long productId, Long shopId) {
		Wrapper<PriceTag> wrapper = new EntityWrapper<>();
		wrapper.eq("productId", productId);
		wrapper.eq("shopId", shopId);
		List<PriceTag> selectList = priceTagMapper.selectList(wrapper);
		if (selectList.size() > 0) {
			return selectList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Long getSumByStock(Long goodsId, Long shopId) {

		return this.baseMapper.getSumByStock(goodsId, shopId);
	}

	@Override
	public List<PriceTag> getProdectListByGandS(Long goodsId, Long shopId) {
		Wrapper<PriceTag> wrapper = new EntityWrapper<>();
		wrapper.eq("goodsId", goodsId);
		wrapper.eq("shopId", shopId);
		wrapper.eq("marketable", 1);
		List<PriceTag> priceTagList = priceTagMapper.selectList(wrapper);
		return priceTagList;
	}

}
