package com.stylefeng.guns.order;

import com.md.goods.model.PriceTag;
import com.md.member.dao.MemberMapper;
import com.md.member.model.Member;
import com.md.member.service.IMemberService;
import com.md.order.constant.OrderStatus;
import com.md.order.dao.OrderMapper;
import com.md.order.factory.OrderFactory;
import com.md.order.model.Order;
import com.md.order.model.ShopItem;
import com.md.order.service.IOrderService;
import com.md.settlement.service.IAccountService;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.core.util.DateUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class OrderTest extends BaseJunit {
	
	@Resource
	IOrderService orderService;
	@Resource
	OrderMapper orderMapper;
	@Resource
	MemberMapper customerMapper;
	@Resource
	IMemberService customerService;
	@Resource
	IAccountService accountService;
	Order order1;
	Order order2;
	Order order3;

	Member customer1;
	Member customer2;
	Member customer3;

	ShopItem shopItem1;
	ShopItem shopItem2;
	ShopItem shopItem3;

	PriceTag priceTag1;
	PriceTag priceTag2;
	PriceTag priceTag3;

	List<ShopItem> shopItems=new ArrayList<>();
	Map<Long,PriceTag> priceTagMap=new HashMap<>();
	@Before
	public void init(){
		customer1=new Member();
		customer1.setId(10L);
		customer1.setName("张三");
		customerMapper.insert(customer1);

		customer2= new Member();
		customer2.setId(2L);
		customer2.setName("李四");
		customerMapper.insert(customer2);

		customer3 = new Member();
		customer3.setId(3L);
		customer3.setName("王五");
		customerMapper.insert(customer3);

		String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
		order1=new Order();
		order1.setId(1L);
		order1.setSn(format+"0001");
		order1.setMemberId(customer1.getId());
		order1.setStatus(0);
		orderMapper.insert(order1);

		order2=new Order();
		order2.setId(2L);
		order2.setSn(format+"0002");
		order2.setMemberId(customer2.getId());
		order1.setStatus(1);
		orderMapper.insert(order2);

		order3 =new Order();
		order3.setId(3L);
		order3.setSn(format+"0003");
		order3.setMemberId(customer3.getId());
		order1.setStatus(2);
		orderMapper.insert(order3);

		shopItem1=new ShopItem();
		shopItem1.setGoodsId(100L);
		shopItem1.setProductId(100L);
		shopItem1.setQuantity(1);
		shopItem1.setGoodsName("百事可乐");
		shopItems.add(shopItem1);

		shopItem2=new ShopItem();
		shopItem2.setGoodsId(200L);
		shopItem2.setProductId(200L);
		shopItem2.setQuantity(2);
		shopItem2.setGoodsName("好吃点");
		shopItems.add(shopItem2);

		shopItem3=new ShopItem();
		shopItem3.setGoodsId(300L);
		shopItem3.setProductId(300L);
		shopItem3.setQuantity(3);
		shopItem1.setGoodsName("德芙巧克力");
		shopItems.add(shopItem3);

		priceTag1=new PriceTag();
		priceTag1.setProductId(100L);
		priceTag1.setMarketPrice(new BigDecimal(10));
		priceTag1.setPrice(new BigDecimal(9.9));
		priceTagMap.put(priceTag1.getProductId(),priceTag1);

		priceTag2=new PriceTag();
		priceTag2.setProductId(200L);
		priceTag2.setMarketPrice(new BigDecimal(15));
		priceTag2.setPrice(new BigDecimal(13.9));
		priceTagMap.put(priceTag2.getProductId(),priceTag2);

		priceTag3=new PriceTag();
		priceTag3.setProductId(300L);
		priceTag3.setMarketPrice(new BigDecimal(12));
		priceTag3.setPrice(new BigDecimal(11.9));
		priceTagMap.put(priceTag3.getProductId(),priceTag3);

	}
	@After
	public void destroy(){
		orderMapper.deleteById(order1.getId());
		orderMapper.deleteById(order2.getId());
		orderMapper.deleteById(order3.getId());

		customerMapper.deleteById(customer1.getId());
		customerMapper.deleteById(customer2.getId());
		customerMapper.deleteById(customer3.getId());

		order1=null;
		order2=null;
		order3 =null;

		customer1=null;
		customer2=null;
		customer3=null;

		shopItem1=null;
		shopItem2=null;
		shopItem3=null;

		priceTag1=null;
		priceTag2=null;
		priceTag3=null;
	}
	/**
	 * 根据订单编号查找订单列表
	 */
	@Test
	public void getListBySn(){
		String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
		Order order=new Order();
		order.setSn(format+"0001");
		List<Map<String,Object>> orderList=orderService.find(order);
		long expected=1;
		long actual=orderList.stream().filter(o->o.get("sn").equals(order.getSn())).count();
		assertEquals(expected,actual);
	}
	/**
	 * 根据会员名称查找订单列表
	 */
	@Test
	public void getListByCustomerName(){
		Member customer=new Member();
		customer.setName("张三");
		List<Map<String,Object>> customers=customerService.find(customer);
		List<Map<String,Object>> orderList=new ArrayList<>();
		customers.stream().forEach(c->{
			Order order=new Order();
			order.setMemberId((Long)c.get("customerId"));
			orderList.addAll(orderService.find(order));
		});
		long expected=1;
		long actual=orderList.size();
		assertEquals(expected,actual);
	}
	/**
	 * 根据订单状态查看订单
	 */
	@Test
	public void getListByStatus(){
		Order order=new Order();
		order.setStatus(0);
		List<Map<String,Object>> orderList=orderService.find(order);
		long expected=1;
		long actual=orderList.stream().filter(o->o.get("status").equals(order.getStatus())).count();
		assertEquals(expected,actual);

	}

	/**
	 * 根据顾客姓名和订单编号查询订单
	 */
	@Test
	public void getListByCustomerNameAndSn(){
		Member customer=new Member();
		customer.setName("张三");
		List<Map<String,Object>> customers=customerService.find(customer);
		List<Map<String,Object>> orderList=new ArrayList<>();
		customers.stream().forEach(c->{
			Order order=new Order();
			order.setSn("201804020001");
			order.setMemberId((Long)c.get("customerId"));
			orderList.addAll(orderService.find(order));
		});
		long expected=1;
		long actual=orderList.size();
		assertEquals(expected,actual);
	}
	/**
	 * 根据顾客姓名、订单编号、状态查询订单
	 */
	@Test
	public void getListByCustomerNameAndSnAndStatus(){
		Member customer=new Member();
		customer.setName("张三");
		List<Map<String,Object>> customers=customerService.find(customer);
		List<Map<String,Object>> orderList=new ArrayList<>();
		customers.stream().forEach(c->{
			Order order=new Order();
			order.setSn("201804020001");
			order.setStatus(0);
			order.setMemberId((Long)c.get("customerId"));
			orderList.addAll(orderService.find(order));
		});
		long expected=1;
		long actual=orderList.size();
		assertEquals(expected,actual);
	}
	/**
	 * 获取新的流水号，是当天第一条
	 *
	 */
	@Test
	public void getNewSn_one(){
		String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String expected=format+"0004";
		String actual= OrderFactory.me().getNewSn();
		assertEquals(expected,actual);
	}
	/**
	 * 获取新的流水号,不是当天的第一条
	 *
	 */
	@Test
	public void getNewSn_noOne(){
		Order order=new Order();
		String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String str=format+"0003";
		order.setId(200L);
		order.setSn(str);
		orderMapper.insert(order);
		String expected=format+"0004";
		String actual= OrderFactory.me().getNewSn();
		assertEquals(expected,actual);
	}

	
	//测试更改订单状态的方法
	//将订单状态更改为未发货
	@Test
	public void changeStatus_waitSend(){
		orderService.editStatus(order1.getId(), OrderStatus.WAIT_SEND);
		Integer expected=OrderStatus.WAIT_SEND.getCode();
		Integer actual = orderMapper.selectById(order1.getId()).getStatus();
		assertEquals(expected, actual);
	}

	//将订单状态更改为未发货
	@Test
	public void changeStatus_close(){
		orderService.editStatus(order1.getId(), OrderStatus.TRADE_CLOSE);
		Integer expected=OrderStatus.TRADE_CLOSE.getCode();
		Integer actual = orderMapper.selectById(order1.getId()).getStatus();
		assertEquals(expected, actual);
	}
	
	//测试更改订单状态的方法
	//将订单状态更改为未发货
	@Test
	public void editRemark_123abc(){
		orderService.editRemark(order1.getId(), "123abc");
		String expected="123abc";
		String actual = orderMapper.selectById(order1.getId()).getRemark();
		assertEquals(expected, actual);
	}
}
