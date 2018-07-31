package com.stylefeng.guns.goods;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.md.goods.dao.PriceTagMapper;
import com.md.goods.model.PriceTag;
import com.md.goods.service.IPriceTagService;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.common.constant.state.Marketable;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PriceTagTest extends BaseJunit {
	@Resource
	PriceTagMapper priceTagMapper;
	@Resource
	IPriceTagService priceTagService;
	private PriceTag priceTag1;
	private PriceTag priceTag2;

	@Before
	public void init() {
		priceTag1 = new PriceTag();
		priceTag1.setId(1L);
		priceTag1.setMarketable(Marketable.UPPER.getCode());
		priceTagMapper.insert(priceTag1);

		priceTag2 = new PriceTag();
		priceTag2.setId(2L);
		priceTag2.setMarketable(Marketable.UPPER.getCode());
		priceTagMapper.insert(priceTag2);
	}

	@After
	public void destory() {
		priceTagMapper.deleteById(1L);
		priceTag1 = null;

		priceTagMapper.deleteById(2L);
		priceTag2 = null;
	}

	// 测试下架
	// 输入商品id为1L
	@Test
	public void lower_id1L() {
		Integer expected = Marketable.LOWER.getCode();
		priceTagService.lower(1L);
		Integer actual = priceTagMapper.selectById(1L).getMarketable();
		assertEquals(expected, actual);
	}

	// 输入商品id为2L
	@Test
	public void lower_id2L() {
		Integer expected = Marketable.LOWER.getCode();
		priceTagService.lower(2L);
		Integer actual = priceTagMapper.selectById(2L).getMarketable();
		assertEquals(expected, actual);
	}

	// 测试上架
	// 输入商品id为1L
	@Test
	public void upper_id1L() {
		priceTagService.lower(1L);
		Integer expected = Marketable.UPPER.getCode();
		priceTagService.upper(1L);
		Integer actual = priceTagMapper.selectById(1L).getMarketable();
		assertEquals(expected, actual);
	}

	// 输入商品id为2L
	@Test
	public void upper_id2L() {
		priceTagService.lower(2L);
		Integer expected = Marketable.UPPER.getCode();
		priceTagService.upper(2L);
		Integer actual = priceTagMapper.selectById(2L).getMarketable();
		assertEquals(expected, actual);
	}

	/**
	 * 增加商品库存
	 */
	@Test
	public void addInventory(){
		Long productId = 980621389433806849L;
		Long shopId = 978156925681840130L;
		Integer amount = 2;
		priceTagService.addInventory(productId,shopId,amount);
		Integer expected = 32;
		PriceTag priceTag = new PriceTag();
		priceTag.setProductId(productId);
		priceTag.setShopId(shopId);
		Integer actual = priceTagService.findOne(priceTag).getInventory();
		assertEquals(expected, actual);

	}
	/**
	 * 减少商品库存
	 */
	@Test
	public void reduceInventory(){
		Long productId = 980621389433806849L;
		Long shopId = 978156925681840130L;
		Integer amount = 2;
		priceTagService.reduceInventory(productId,shopId,amount);
		Integer expected = 30;
		PriceTag priceTag = new PriceTag();
		priceTag.setProductId(productId);
		priceTag.setShopId(shopId);
		Integer actual = priceTagService.findOne(priceTag).getInventory();
		assertEquals(expected, actual);

	}
	
}
