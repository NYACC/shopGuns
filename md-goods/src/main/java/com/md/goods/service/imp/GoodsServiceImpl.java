package com.md.goods.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.goods.dao.GoodsMapper;
import com.md.goods.dao.ProductMapper;
import com.md.goods.model.Category;
import com.md.goods.model.Goods;
import com.md.goods.model.Product;
import com.md.goods.service.IGoodsService;
import com.stylefeng.guns.core.constant.CategoryStatus;
import com.stylefeng.guns.core.page.Page;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
@Transactional
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
	@Resource
	GoodsMapper goodsMapper;
	@Resource
	ProductMapper productMapper;

	@Override
	public boolean existGoods(String name, Long goodsId) {
		Wrapper<Goods> wrapper = new EntityWrapper<>();
		wrapper.eq("name", name);
		List<Goods> goodsList = goodsMapper.selectList(wrapper);
		if (goodsList.size() > 0) {
			if (!goodsList.get(0).getId().equals(goodsId)) {
				return true;
			}

		}
		return false;
	}

	@Override
	public List<Map<String, Object>> find(Goods goods, String barcode) {
		Wrapper<Goods> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(barcode)) {
			// 根据条码查找规格商品
			Wrapper<Product> w = new EntityWrapper<>();
			w.like("barcode", barcode);
			List<Product> productList = this.productMapper.selectList(w);
			// 获取商品的编号集合
			List<Long> goodsIds = new ArrayList<>();
			goodsIds.add(0L);
			if (ToolUtil.isNotEmpty(productList)) {
				for (Product product : productList) {
					goodsIds.add(product.getGoodsId());
				}
			}
			wrapper.in("id", goodsIds);
		}
		// 根据条码或名称查找商品
		if (ToolUtil.isNotEmpty(goods.getName())) {
			wrapper.like("name", goods.getName());
		}
		wrapper.orderBy("createTime", false);
		List<Map<String, Object>> goodsList = goodsMapper.selectMaps(wrapper);
		return goodsList;
	}

	@Override
	public Goods findById(Long id) {
		return this.goodsMapper.selectById(id);
	}

	@Override
	public String getNewSn() {
		String currentTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		// 获取数据库中最大的是sn码(判断是否为空)
		String maxSn = goodsMapper.getMaxSn(currentTime);
		if (ToolUtil.isNotEmpty(maxSn)) {
			// 如果最大sn为空则为当天的第二条
			return (Long.parseLong(maxSn) + 1L) + "";
		}
		return currentTime + "0001";
	}

	@Override
	public Long add(Goods goods) {
		goodsMapper.insert(goods);
		return goods.getId();
	}

	@Override
	public void edit(Goods goods) {
		goodsMapper.updateById(goods);
	}

	@Override
	public Boolean specIsUse(Long id) {
		Wrapper<Goods> wrapper = new EntityWrapper<>();
		wrapper.like("specItems", "{\"id\":\"" + id + "\",\"name\"");
		if (goodsMapper.selectCount(wrapper) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> findGoodsByParamId(Long ItemId) {
		Wrapper<Goods> wrapper = new EntityWrapper<>();
		wrapper.like("paramItems", "\"" + ItemId + "\"");
		return goodsMapper.selectMaps(wrapper);
	}

	@Override
	public List<Map<String, Object>> goodsList(String shopIds, Long categoryId, Long brandId, String goodsName) {
		List<Map<String, Object>> goodsList = goodsMapper.goodsList(shopIds, categoryId, brandId, goodsName);
		return goodsList;
	}

	@Override
	public List<Map<String, Object>> findBindPriceTag(List<Long> ids) {
		if (ToolUtil.isNotEmpty(ids)) {
			List<Map<String, Object>> priceTagList = goodsMapper.findBindPriceTag(ids);
			return priceTagList;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getListByCate(Category category, Long shopId, Integer index) {
		Wrapper<Goods> wrapper = new EntityWrapper<>();
		Integer begin = (index - 1) * Page.PAGESIZE.getCode();
		if (category.getStatus() != CategoryStatus.DISABLE.getCode()) {
			if (category.getLevel() == 1) {
				wrapper.where(
						"id IN (SELECT scr.goodsId FROM shop_category_relation AS scr LEFT JOIN shop_category AS sc ON scr.categoryId = sc.id LEFT JOIN shop_price_tag AS spt ON spt.goodsId = scr.goodsId WHERE sc.pid = "
								+ category.getId() + " AND spt.marketable = 1" + " AND spt.shopId = " + shopId + ")");

			} else if (category.getLevel() == 2) {
				wrapper.where(
						"id IN (SELECT scr.goodsId FROM shop_category_relation AS scr LEFT JOIN shop_category AS sc ON scr.categoryId = sc.id LEFT JOIN shop_price_tag AS spt ON spt.goodsId = scr.goodsId WHERE sc.id = "
								+ category.getId() + " AND spt.marketable = 1" + " AND spt.shopId = " + shopId + ")");
			}
		}
		wrapper.orderBy("id");
		RowBounds rowBounds = new RowBounds(begin, Page.PAGESIZE.getCode());
		return goodsMapper.selectMapsPage(rowBounds, wrapper);
	}

	@Override
	public List<Map<String, Object>> getListByName(String name, Long shopId, Integer index) {
		Wrapper<Goods> wrapper = new EntityWrapper<>();
		Integer begin = (index - 1) * Page.PAGESIZE.getCode();
		wrapper.like("name", name);
		wrapper.where(
				"id in (select goodsId from shop_price_tag where spt.marketable = 1 and shopId = " + shopId + ")");
		wrapper.orderBy("id");
		RowBounds rowBounds = new RowBounds(begin, Page.PAGESIZE.getCode());
		return goodsMapper.selectMapsPage(rowBounds, wrapper);
	}

	@Override
	public List<Map<String, Object>> getListByTag(Long tagId, Long shopId, Integer index) {
		Wrapper<Goods> wrapper = new EntityWrapper<>();
		Integer begin = (index - 1) * Page.PAGESIZE.getCode();
		wrapper.where(
				"id IN (SELECT str.goodsId FROM shop_tag_relation AS str LEFT JOIN shop_tag AS st ON str.tagId = st.id LEFT JOIN shop_price_tag AS spt ON spt.goodsId = str.goodsId WHERE st.id = "+tagId+" AND spt.marketable = 1 AND spt.shopId = "+shopId+")");
		wrapper.orderBy("id");
		RowBounds rowBounds = new RowBounds(begin, Page.PAGESIZE.getCode());
		return goodsMapper.selectMapsPage(rowBounds, wrapper);
	}

}
