package com.stylefeng.guns.order;

import com.md.order.dao.InventoryMapper;
import com.md.order.model.Inventory;
import com.md.order.service.IInventoryService;
import com.stylefeng.guns.base.BaseJunit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class InventoryTest extends BaseJunit {
	
	private Inventory inventory1;
	private Inventory inventory2;
	private Inventory inventory3;
	private Inventory queryObj;
	@Resource
	InventoryMapper inventoryMapper;
	@Resource
	IInventoryService inventoryService;
	
	@Before
	public void init() throws ParseException{
		inventory1 = new Inventory();
		inventory1.setId(1L);
		inventory1.setBarcode("100001");
		inventory1.setOperatorId(1);
		inventory1.setGoodsName("薯片普通装");
		inventory1.setShopId(1L);
		inventory1.setType(0);
		inventory1.setCreateTime(new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-10 00:00:01")).getTime()));
		inventoryMapper.insert(inventory1);
		
		inventory2 = new Inventory();
		inventory2.setId(2L);
		inventory2.setBarcode("100002");
		inventory2.setOperatorId(1);
		inventory2.setGoodsName("薯片家庭装");
		inventory2.setShopId(2L);
		inventory2.setType(0);
		inventory2.setCreateTime(new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-11 00:00:02")).getTime()));
		inventoryMapper.insert(inventory2);
		
		inventory3 = new Inventory();
		inventory3.setId(3L);
		inventory3.setBarcode("100003");
		inventory3.setOperatorId(2);
		inventory3.setGoodsName("薯片精品装");
		inventory3.setShopId(2L);
		inventory3.setType(1);
		inventory3.setCreateTime(new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-12 00:00:03")).getTime()));
		inventoryMapper.insert(inventory3);
		
		queryObj = new Inventory();
	}
	
	@After
	public void destroy(){
		inventoryMapper.deleteById(1L);
		inventoryMapper.deleteById(2L);
		inventoryMapper.deleteById(3L);
		inventory1=null;
		inventory2=null;
		inventory3=null;
		queryObj=null;
	}
	
	//出入库记录搜索的测试
	//根据商品名称查询的测试
	@Test
	public void find_nameShuPianJinPinZhuang(){
		queryObj.setGoodsName("薯片精品装");
		Integer actual=inventoryService.find(queryObj, null, null, null, null).size();
		Integer expected=1;
		assertEquals(expected,actual);
	}

	//根据商品条码查询的测试
	@Test
	public void find_barcode100002(){
		queryObj.setBarcode("100002");
		Integer actual=inventoryService.find(queryObj, null, null, null, null).size();
		Integer expected=1;
		assertEquals(expected,actual);
	}

	//根据门店名称查询的测试
	@Test
	public void find_shopIds1(){
		Long shopId = new Long("1");
		Integer actual=inventoryService.find(null, shopId, null, null, null).size();
		Integer expected=1;
		assertEquals(expected,actual);
	}

	//根据操作员名称查询的测试
	@Test
	public void find_operatorIds1and2(){
		ArrayList<Integer> operatorIds = new ArrayList<>();
		operatorIds.add(1);
		operatorIds.add(2);
		Integer actual=inventoryService.find(null, null, operatorIds, null, null).size();
		Integer expected=3;
		assertEquals(expected,actual);
	}

	//根据操作类型（出入库）查询的测试
	@Test
	public void find_type(){
		queryObj.setType(1);
		Integer actual=inventoryService.find(queryObj, null, null, null, null).size();
		Integer expected=1;
		assertEquals(expected,actual);
	}

	//根据时间段查询的测试
	@Test
	public void find_dateTime() throws ParseException{
		Timestamp startTime = new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-10 00:00:00")).getTime());
		Timestamp endTime = new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-10 12:00:00")).getTime());
		Integer actual=inventoryService.find(null, null, null, startTime, endTime).size();
		Integer expected=1;
		assertEquals(expected,actual);
	}
	
	//组合查询的测试
	@Test
	public void find_zuhe() throws ParseException{
		queryObj.setType(0);
		Timestamp startTime = new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-10 00:00:00")).getTime());
		Timestamp endTime = new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-12 12:00:00")).getTime());
		Integer actual=inventoryService.find(queryObj, null, null, startTime, endTime).size();
		Integer expected=2;
		assertEquals(expected,actual);
	}
}
