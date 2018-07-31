package com.stylefeng.guns.goods;

import com.md.goods.dao.GoodsMapper;
import com.md.goods.dao.ProductMapper;
import com.md.goods.model.Goods;
import com.md.goods.model.Product;
import com.md.goods.service.IGoodsService;
import com.stylefeng.guns.base.BaseJunit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GoodsTest extends BaseJunit {
    @Resource
	IGoodsService goodsService;
    Goods goods1;
    Goods goods2;
    Goods goods3;
    Goods queryObj;
    Product product;
    List<Goods> expectedList;
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    ProductMapper productMapper;

    @Before
    public void init() {
        expectedList = new ArrayList<>();
        goods1 = new Goods();
        goods1.setId(1L);
        goods1.setName("德芙巧克力");
        goods1.setBrandId(961055685445668865L);
        expectedList.add(goods1);
        goodsMapper.insert(goods1);
        product = new Product();
        product.setBarcode("201800001");
        product.setGoodsId(1L);
        product.setId(1L);
        productMapper.insert(product);

		goods2=new Goods();
		goods2.setId(2L);
		goods2.setName("力士沐浴露");
		goods2.setBrandId(961054839953571842L);
		expectedList.add(goods2);
		goodsMapper.insert(goods2);

		goods3=new Goods();
		goods3.setId(3L);
		goods3.setName("潘婷洗发水");
		goods3.setBrandId(961055770774589441L);
		expectedList.add(goods3);
		goodsMapper.insert(goods3);

		queryObj=new Goods();
	}
	@After
	public void destroy(){
		goodsMapper.deleteById(goods1.getId());
		goodsMapper.deleteById(goods2.getId());
		goodsMapper.deleteById(goods3.getId());
		productMapper.deleteById(product.getId());
		goods1=null;
		goods2=null;
		goods3=null;
		queryObj=null;
		product=null;
		goodsMapper.deleteById(200L);
	}

	/**
	 * 添加商品的时候要判断商品库中是否有同样的商品，根据商品名称和条码判断商品是否存在
	 * 判断商品重复的时候，要在整个商品库中查找商品，包括上架区和下架区
	 */

	@Test
	public void exitGoodsByName(){
		String name="潘婷洗发水";
		Boolean flag=goodsService.existGoods(name,3L);
		assertTrue(flag);
	}

	/**
	 * 测试根据商品的名称和条码查询方法
	 * 输入名称=德芙巧克力，条码=null
	 */
	@Test
	public void find_nameDeFuQiaoKeLe_barcodeNull(){
		queryObj.setName("德芙巧克力");
		String barcode=null;
		Integer expected=1;
		Integer actual=goodsService.find(queryObj,barcode).size();
		assertEquals(expected,actual);
	}
	/**
	 * 测试根据商品的名称和条码查询方法
	 * 输入名称=null，条码=201800001
	 */
	@Test
	public void find_nameNull_barcode201800001(){
		String barcode="201800001";
		Integer expected=1;
		Integer actual=goodsService.find(queryObj,barcode).size();
		assertEquals(expected,actual);
	}
	/**
	 * 测试根据商品的名称和条码查询方法
	 * 输入名称=null，条码=null
	 */
	@Test
	public void find_nameNull_barcodeNull(){
		String barcode=null;
		Integer expected=3;
		Integer actual=goodsService.find(queryObj,barcode).size();
		assertEquals(expected,actual);
	}
	/**
	 * 测试根据商品的名称和条码查询方法
	 * 输入名称="德芙巧克力"，条码="201800001"
	 */
	@Test
	public void find_nameDeFuQiaoKeLe_barcode201800001(){
		queryObj.setName("德芙巧克力");
		String barcode="201800001";
		Integer expected=1;
		Integer actual=goodsService.find(queryObj,barcode).size();
		assertEquals(expected,actual);
	}
	/**
	 * 测试根据商品的名称和条码查询方法
	 * 输入名称="德芙巧克力"，条码="201800002"
	 */
	@Test
	public void find_nameDeFuQiaoKeLe_barcode201800002(){
		queryObj.setName("德芙巧克力");
		String barcode="201800002";
		Integer expected=0;
		Integer actual=goodsService.find(queryObj,barcode).size();
		assertEquals(expected,actual);
	}
	/**
	 * 获取新的流水号，是当天第一条
	 * 
	 */
	@Test
	public void getNewSn_one(){
		String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String expected=format+"0001";
		String actual=goodsService.getNewSn();
		assertEquals(expected,actual);
	}
	/**
	 * 获取新的流水号,不是当天的第一条
	 * 
	 */
	@Test
	public void getNewSn_noOne(){
		Goods goods4 = new Goods();
		String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String str=format+"0003";
		goods4.setId(200L);
		goods4.setSn(str);
		goodsMapper.insert(goods4);
		String expected=format+"0004";
		String actual=goodsService.getNewSn();
		assertEquals(expected,actual);
	}
}