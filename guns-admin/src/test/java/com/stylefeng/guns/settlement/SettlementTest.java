package com.stylefeng.guns.settlement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.md.coupon.constant.CodeStatus;
import com.md.coupon.constant.PromotionModel;
import com.md.coupon.dao.CouponCodeMapper;
import com.md.coupon.dao.CouponMapper;
import com.md.coupon.dao.PromotionMapper;
import com.md.coupon.dao.PromotionPriceTagMapper;
import com.md.coupon.model.Coupon;
import com.md.coupon.model.CouponCode;
import com.md.coupon.model.Promotion;
import com.md.coupon.model.PromotionPriceTag;
import com.md.delivery.dao.DeliveryCostMapper;
import com.md.delivery.dao.DeliveryModeMapper;
import com.md.delivery.model.DeliveryCost;
import com.md.delivery.model.DeliveryMode;
import com.md.goods.dao.GoodsMapper;
import com.md.goods.dao.PriceTagMapper;
import com.md.goods.dao.ProductMapper;
import com.md.goods.dao.ShopMapper;
import com.md.goods.model.Goods;
import com.md.goods.model.PriceTag;
import com.md.goods.model.Product;
import com.md.goods.model.Shop;
import com.md.member.dao.AddressMapper;
import com.md.member.model.Address;
import com.md.order.model.Order;
import com.md.order.model.ShopItem;
import com.md.settlement.service.IAccountService;
import com.stylefeng.guns.base.BaseJunit;

public class SettlementTest extends BaseJunit {

	@Resource
	PriceTagMapper priceTagMapper;
	@Resource
	IAccountService accountService;
	@Resource
	PromotionMapper promotionMapper;
	@Resource
	PromotionPriceTagMapper promotionPriceTagMapper;
	@Resource
	ProductMapper productMapper;
	@Resource
	DeliveryCostMapper deliveryCostMapper;
	@Resource
	DeliveryModeMapper deliveryModeMapper;
	@Resource
	AddressMapper addressMapper;
	@Resource
	ShopMapper shopMapper;
	@Resource
	GoodsMapper goodsMapper;
	@Resource
	CouponMapper couponMapper;
	@Resource
	CouponCodeMapper couponCodeMapper;

	ShopItem shopItem1;
	ShopItem shopItem2;
	ShopItem shopItem3;

	List<ShopItem> shopItems;

	PriceTag priceTag1;
	PriceTag priceTag2;
	PriceTag priceTag3;
	PriceTag priceTag4;

	Promotion promotion1;
	Promotion promotion2;
	Promotion promotion3;

	PromotionPriceTag promotionPriceTag1;
	PromotionPriceTag promotionPriceTag2;
	PromotionPriceTag promotionPriceTag3;
	PromotionPriceTag promotionPriceTag4;

	Product product1;
	Product product2;
	Product product3;
	Product product4;

	Goods goods1;
	Goods goods2;

	Shop shop;

	Address address;

	DeliveryCost deliveryCost;

	DeliveryMode deliveryMode;

	Coupon coupon;

	CouponCode couponCode;

	@Before
	public void init() throws ParseException {

		shop = new Shop();
		shop.setCountyId(Long.parseLong("2"));
		shopMapper.insert(shop);

		goods1 = new Goods();
		goods1.setName("丽丽薯片");
		goodsMapper.insert(goods1);
		goods2 = new Goods();
		goods2.setName("冰红茶");
		goodsMapper.insert(goods2);

		// 初始化规格商品
		product1 = new Product();
		product1.setWeight(new BigDecimal(1000));
		productMapper.insert(product1);

		product2 = new Product();
		product2.setWeight(new BigDecimal(1500));
		productMapper.insert(product2);

		product3 = new Product();
		product3.setWeight(new BigDecimal(300));
		productMapper.insert(product3);

		product4 = new Product();
		product4.setWeight(new BigDecimal(300));
		productMapper.insert(product4);

		// 添加价格标签的初始数据
		priceTag1 = new PriceTag();
		priceTag1.setGoodsId(goods1.getId());
		priceTag1.setProductId(product1.getId());
		priceTag1.setShopId(shop.getId());
		priceTag1.setMarketPrice(new BigDecimal("10"));
		priceTag1.setPrice(new BigDecimal("8"));
		priceTagMapper.insert(priceTag1);

		priceTag2 = new PriceTag();
		priceTag2.setGoodsId(goods1.getId());
		priceTag2.setProductId(product2.getId());
		priceTag2.setShopId(shop.getId());
		priceTag2.setMarketPrice(new BigDecimal("10"));
		priceTag2.setPrice(new BigDecimal("8"));
		priceTagMapper.insert(priceTag2);

		priceTag3 = new PriceTag();
		priceTag3.setGoodsId(goods2.getId());
		priceTag3.setProductId(product3.getId());
		priceTag3.setShopId(shop.getId());
		priceTag3.setMarketPrice(new BigDecimal("5"));
		priceTag3.setPrice(new BigDecimal("4"));
		priceTagMapper.insert(priceTag3);

		priceTag4 = new PriceTag();
		priceTag4.setGoodsId(goods2.getId());
		priceTag4.setProductId(product4.getId());
		priceTag4.setShopId(shop.getId());
		priceTag4.setMarketPrice(new BigDecimal("5"));
		priceTag4.setPrice(new BigDecimal("4"));
		priceTagMapper.insert(priceTag4);

		// 添加促销的初始数据
		promotion1 = new Promotion();
		promotion1.setModel(PromotionModel.DISCOUNT.getCode());
		promotion1.setDiscount(new BigDecimal(0.7));
		promotion1.setStartTime(
				new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-05-01 00:00:00")).getTime()));
		promotion1.setEndTime(
				new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-05-02 00:00:00")).getTime()));
		promotionMapper.insert(promotion1);

		promotion2 = new Promotion();
		promotion2.setModel(PromotionModel.REDUCE.getCode());
		promotion2.setFulfil(new BigDecimal("15"));
		promotion2.setReduce(new BigDecimal("5"));
		promotion2.setStartTime(
				new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-05-01 00:00:00")).getTime()));
		promotion2.setEndTime(new Timestamp(new Date().getTime() + 1000 * 60 * 60));
		promotionMapper.insert(promotion2);

		promotion3 = new Promotion();
		promotion3.setModel(PromotionModel.DISCOUNT.getCode());
		promotion3.setDiscount(new BigDecimal(0.8));
		promotion3.setStartTime(
				new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-05-01 00:00:00")).getTime()));
		promotion3.setEndTime(new Timestamp(new Date().getTime() + 1000 * 60 * 60));
		promotionMapper.insert(promotion3);

		// 添加促销的关联
		promotionPriceTag1 = new PromotionPriceTag(promotion1.getId(), priceTag1.getId());
		promotionPriceTagMapper.insert(promotionPriceTag1);

		promotionPriceTag2 = new PromotionPriceTag(promotion2.getId(), priceTag1.getId());
		promotionPriceTagMapper.insert(promotionPriceTag2);

		promotionPriceTag3 = new PromotionPriceTag(promotion2.getId(), priceTag2.getId());
		promotionPriceTagMapper.insert(promotionPriceTag3);

		promotionPriceTag4 = new PromotionPriceTag(promotion3.getId(), priceTag3.getId());
		promotionPriceTagMapper.insert(promotionPriceTag4);

		// 初始化配送地址(北京市东城区)
		address = new Address();
		address.setCounty(2L);
		addressMapper.insert(address);

		// 初始化一个配送方式
		deliveryMode = new DeliveryMode();
		deliveryMode.setName("普通配送");
		deliveryModeMapper.insert(deliveryMode);

		// 初始化一个配送费
		deliveryCost = new DeliveryCost();
		deliveryCost.setDeliveryArea(shop.getCountyId());
		deliveryCost.setModeId(deliveryMode.getId());
		deliveryCost.setAreaId(address.getCounty());
		deliveryCost.setYkg(new BigDecimal(1000));
		deliveryCost.setStartPrice(new BigDecimal(10));
		deliveryCost.setAddedWeight(new BigDecimal(500));
		deliveryCost.setAddedPrice(new BigDecimal(2));
		deliveryCostMapper.insert(deliveryCost);

		// 初始化一张优惠卷
		coupon = new Coupon();
		coupon.setFulfil(new BigDecimal(20));
		coupon.setReduce(new BigDecimal(2));
		coupon.setShopId(shop.getId());
		coupon.setUseStart(
				new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-05-01 00:00:00")).getTime()));
		coupon.setUseEnd(new Timestamp(new Date().getTime() + 1000 * 60 * 60));
		couponMapper.insert(coupon);

		// 初始化优惠码
		couponCode = new CouponCode();
		couponCode.setCode("cc001");
		couponCode.setCouponId(coupon.getId());
		couponCode.setStatus(CodeStatus.RECEIVED.getCode());
		couponCodeMapper.insert(couponCode);
	}

	@After
	public void destroy() {

		shopItem1 = null;
		shopItem2 = null;
		shopItem3 = null;
		shopItems = null;

		productMapper.deleteById(product1.getId());
		productMapper.deleteById(product2.getId());
		productMapper.deleteById(product3.getId());
		productMapper.deleteById(product4.getId());
		product1 = null;
		product2 = null;
		product3 = null;
		product4 = null;

		priceTagMapper.deleteById(priceTag1.getId());
		priceTagMapper.deleteById(priceTag2.getId());
		priceTagMapper.deleteById(priceTag3.getId());
		priceTagMapper.deleteById(priceTag4.getId());
		priceTag1 = null;
		priceTag2 = null;
		priceTag3 = null;
		priceTag4 = null;

		promotionMapper.deleteById(promotion1.getId());
		promotionMapper.deleteById(promotion2.getId());
		promotionMapper.deleteById(promotion3.getId());
		promotion1 = null;
		promotion2 = null;
		promotion3 = null;

		promotionPriceTagMapper.deleteById(promotionPriceTag1.getId());
		promotionPriceTagMapper.deleteById(promotionPriceTag2.getId());
		promotionPriceTagMapper.deleteById(promotionPriceTag3.getId());
		promotionPriceTagMapper.deleteById(promotionPriceTag4.getId());
		promotionPriceTag1 = null;
		promotionPriceTag2 = null;
		promotionPriceTag3 = null;
		promotionPriceTag4 = null;

		deliveryModeMapper.deleteById(deliveryMode.getId());
		deliveryMode = null;

		addressMapper.deleteById(address.getId());
		address = null;

		deliveryCostMapper.deleteById(deliveryCost.getId());
		deliveryCost = null;

		shopMapper.deleteById(shop.getId());
		shop = null;

		goodsMapper.deleteById(goods1.getId());
		goods1 = null;
		goodsMapper.deleteById(goods2.getId());
		goods2 = null;

		couponMapper.deleteById(coupon.getId());
		coupon = null;

		couponCodeMapper.deleteById(couponCode.getId());
		couponCode = null;
	}

	// 测试获取商品清单的价格签
	@Test
	public void findPriceTags() {
		// 初始化购买清单
		shopItem1 = new ShopItem(goods1.getId(), product1.getId(), 1, "丽丽薯片普通装", shop.getId());
		shopItem2 = new ShopItem(goods1.getId(), product2.getId(), 1, "丽丽薯片精品装", shop.getId());
		shopItem3 = new ShopItem(goods2.getId(), product3.getId(), 2, "冰红茶盒装", shop.getId());
		shopItems = new ArrayList<>();
		shopItems.add(shopItem1);
		shopItems.add(shopItem2);
		shopItems.add(shopItem3);
		// 获取商品清单的价格签
		Map<Long, PriceTag> priceTags = accountService.findPriceTag(shopItems);

		if (priceTags == null) {
			assertTrue(false);
			return;
		}
		assertEquals(priceTag1.getId(), priceTags.get(shopItem1.getProductId()).getId());
		assertEquals(priceTag2.getId(), priceTags.get(shopItem2.getProductId()).getId());
		assertEquals(priceTag3.getId(), priceTags.get(shopItem3.getProductId()).getId());
	}

	// 测试有商品清单，获取促销分组信息
	@Test
	public void findPromotions() {
		// 初始化购买清单
		shopItem1 = new ShopItem(goods1.getId(), product1.getId(), 1, "丽丽薯片普通装", shop.getId());
		shopItem2 = new ShopItem(goods1.getId(), product2.getId(), 1, "丽丽薯片精品装", shop.getId());
		shopItem3 = new ShopItem(goods2.getId(), product3.getId(), 2, "冰红茶盒装", shop.getId());
		shopItems = new ArrayList<>();
		shopItems.add(shopItem1);
		shopItems.add(shopItem2);
		shopItems.add(shopItem3);

		// 获取商品清单的价格签
		Map<Long, PriceTag> priceTags = accountService.findPriceTag(shopItems);
		// 获取购买清单的促销分组信息
		Map<Long, List<Long>> promotions = accountService.findPromotions(priceTags);

		if (promotions == null) {
			assertTrue(false);
			return;
		}
		assertEquals(null, promotions.get(promotion1.getId()));// 促销1已经过了，所以取不到
		int actulSize2 = promotions.get(promotion2.getId()).size();
		int expectedSize2 = 2;
		assertEquals(actulSize2, expectedSize2);// 购买清单中，共有2件商品参与促销2
		int actulSize3 = promotions.get(promotion3.getId()).size();
		int expectedSize3 = 1;
		assertEquals(actulSize3, expectedSize3);// 购买清单中，共有1件商品参与促销3
	}

	// 测试购买丽丽薯片普通装1袋，精品装1袋子，冰红茶盒装2盒子,将促销加进来不配送
	@Test
	public void settlementPromotion_SP1_SJ1_BH2() {
		// 初始化购买清单
		shopItem1 = new ShopItem(goods1.getId(), product1.getId(), 1, "丽丽薯片普通装", shop.getId());
		shopItem2 = new ShopItem(goods1.getId(), product2.getId(), 1, "丽丽薯片精品装", shop.getId());
		shopItem3 = new ShopItem(goods2.getId(), product3.getId(), 2, "冰红茶盒装", shop.getId());
		shopItems = new ArrayList<>();
		shopItems.add(shopItem1);
		shopItems.add(shopItem2);
		shopItems.add(shopItem3);
		// 结算
		Order order = accountService.amount(shop.getId(), shopItems, null, null, null);

		BigDecimal actualDue = order.getDue();
		BigDecimal actualActualPay = order.getActualPay();

		BigDecimal expectedDue = new BigDecimal("24.00");
		BigDecimal expectedActualPay = new BigDecimal("17.40");
		assertEquals(expectedDue, actualDue);
		assertEquals(expectedActualPay, actualActualPay);
	}

	// 测试购买丽丽薯片普通装2袋，精品装3袋子，冰红茶盒装0盒子,将促销加进来不配送
	@Test
	public void settlementPromotion_SP2_SJ3_BH0() {
		// 初始化购买清单
		shopItem1 = new ShopItem(goods1.getId(), product1.getId(), 2, "丽丽薯片普通装", shop.getId());
		shopItem2 = new ShopItem(goods1.getId(), product2.getId(), 3, "丽丽薯片精品装", shop.getId());
		shopItem3 = new ShopItem(goods2.getId(), product3.getId(), 0, "冰红茶盒装", shop.getId());
		shopItems = new ArrayList<>();
		shopItems.add(shopItem1);
		shopItems.add(shopItem2);
		shopItems.add(shopItem3);
		// 结算
		Order order = accountService.amount(shop.getId(), shopItems, null, null, null);

		BigDecimal actualDue = order.getDue();
		BigDecimal actualActualPay = order.getActualPay();

		BigDecimal expectedDue = new BigDecimal("40.00");
		BigDecimal expectedActualPay = new BigDecimal("35.00");
		assertEquals(expectedDue, actualDue);
		assertEquals(expectedActualPay, actualActualPay);
	}

	// 获取配送费
	@Test
	public void getDeliveryCost() {
		DeliveryCost deliveryCost2 = accountService.getDeliveryCost(deliveryMode, address, shop.getId());
		Long expectedDue = deliveryCost.getId();
		Long actualDue = deliveryCost2.getId();
		assertEquals(expectedDue, actualDue);
	}

	// 测试购买丽丽薯片普通装1袋，精品装1袋子，冰红茶盒装2盒子,将促销加进来，将配送费加进来
	@Test
	public void settlementPromotionDelivery_SP1_SJ1_BH2() {
		// 初始化购买清单
		shopItem1 = new ShopItem(goods1.getId(), product1.getId(), 1, "丽丽薯片普通装", shop.getId());
		shopItem2 = new ShopItem(goods1.getId(), product2.getId(), 1, "丽丽薯片精品装", shop.getId());
		shopItem3 = new ShopItem(goods2.getId(), product3.getId(), 2, "冰红茶盒装", shop.getId());
		shopItems = new ArrayList<>();
		shopItems.add(shopItem1);
		shopItems.add(shopItem2);
		shopItems.add(shopItem3);
		// 结算
		Order order = accountService.amount(shop.getId(), shopItems, deliveryMode, address, null);

		BigDecimal actualDue = order.getDue();
		BigDecimal actualActualPay = order.getActualPay();

		BigDecimal expectedDue = new BigDecimal("24.00");
		BigDecimal expectedActualPay = new BigDecimal("37.40");
		assertEquals(expectedDue, actualDue);
		assertEquals(expectedActualPay, actualActualPay);
	}

	// 测试购买丽丽薯片普通装1袋，精品装1袋子，冰红茶盒装2盒子,将促销加进来，将配送费加进来
	@Test
	public void settlementPromotionDelivery_SP0_SJ0_BH2() {
		// 初始化购买清单
		shopItem1 = new ShopItem(goods1.getId(), product1.getId(), 0, "丽丽薯片普通装", shop.getId());
		shopItem2 = new ShopItem(goods1.getId(), product2.getId(), 0, "丽丽薯片精品装", shop.getId());
		shopItem3 = new ShopItem(goods2.getId(), product3.getId(), 2, "冰红茶盒装", shop.getId());
		shopItems = new ArrayList<>();
		shopItems.add(shopItem1);
		shopItems.add(shopItem2);
		shopItems.add(shopItem3);
		// 结算
		Order order = accountService.amount(shop.getId(), shopItems, deliveryMode, address, null);

		BigDecimal actualDue = order.getDue();
		BigDecimal actualActualPay = order.getActualPay();

		BigDecimal expectedDue = new BigDecimal("8.00");// 商品3打8折，总重量为600g
		BigDecimal expectedActualPay = new BigDecimal("16.40");
		assertEquals(expectedDue, actualDue);
		assertEquals(expectedActualPay, actualActualPay);
	}

	// 测试购买丽丽薯片普通装1袋，精品装1袋子，冰红茶盒装2盒子,将促销加进来，将配送费加进来
	@Test
	public void settlementCoupon_SP1_SJ1_BH2() {
		// 初始化购买清单
		shopItem1 = new ShopItem(goods1.getId(), product1.getId(), 1, "丽丽薯片普通装", shop.getId());
		shopItem2 = new ShopItem(goods1.getId(), product2.getId(), 1, "丽丽薯片精品装", shop.getId());
		shopItem3 = new ShopItem(goods2.getId(), product3.getId(), 2, "冰红茶盒装", shop.getId());
		shopItems = new ArrayList<>();
		shopItems.add(shopItem1);
		shopItems.add(shopItem2);
		shopItems.add(shopItem3);
		// 结算
		Order order = accountService.amount(shop.getId(), shopItems, deliveryMode, address, "cc001");

		BigDecimal actualDue = order.getDue();
		BigDecimal actualActualPay = order.getActualPay();

		BigDecimal expectedDue = new BigDecimal("24.00");
		BigDecimal expectedActualPay = new BigDecimal("37.40");
		assertEquals(expectedDue, actualDue);
		assertEquals(expectedActualPay, actualActualPay);
	}

	// 测试购买丽丽薯片普通装1袋，精品装1袋子，冰红茶盒装2盒子,将促销加进来，将配送费加进来
	@Test
	public void settlementCoupon_BP5() {
		// 初始化购买清单
		shopItem1 = new ShopItem(goods2.getId(), product4.getId(), 5, "冰红茶瓶装", shop.getId());
		shopItems = new ArrayList<>();
		shopItems.add(shopItem1);
		// 结算
		Order order = accountService.amount(shop.getId(), shopItems, null, null, "cc001");

		BigDecimal actualDue = order.getDue();
		BigDecimal actualActualPay = order.getActualPay();

		BigDecimal expectedDue = new BigDecimal("18.00");
		BigDecimal expectedActualPay = new BigDecimal("20.00");
		assertEquals(expectedDue, actualDue);
		assertEquals(expectedActualPay, actualActualPay);
	}
}
