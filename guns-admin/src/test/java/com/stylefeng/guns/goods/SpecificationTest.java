package com.stylefeng.guns.goods;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.md.goods.dao.SpecificationItemMapper;
import com.md.goods.dao.SpecificationMapper;
import com.md.goods.model.Specification;
import com.md.goods.model.SpecificationItem;
import com.md.goods.service.ISpecificationItemService;
import com.md.goods.service.ISpecificationService;
import com.stylefeng.guns.base.BaseJunit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpecificationTest extends BaseJunit {

	@Resource
	SpecificationMapper specificationMapper;
	@Resource
	SpecificationItemMapper specificationItemMapper;
	@Resource
	ISpecificationService specificationService;
	@Resource
	ISpecificationItemService specificationItemService;
	
	private Specification specification1;
	private Specification specification2;
	private Specification specification3;
	private SpecificationItem specificationItem1;
	private Specification queryObj;

	@Before
	public void init() {
		specificationItem1=new SpecificationItem();
		specification1 = new Specification();
		specification1.setId(1L);
		specification1.setName("口味");
		specification1.setSequence(1);
		specification1.setCategoryId(1L);
		specificationMapper.insert(specification1);
		specificationItem1.setId(1L);
		specificationItem1.setName("草莓味");
		specificationItem1.setPid(1L);
		specificationItemMapper.insert(specificationItem1);
		specificationItem1.setId(2L);
		specificationItem1.setName("香草味");
		specificationItem1.setPid(1L);
		specificationItemMapper.insert(specificationItem1);

		specification2 = new Specification();
		specification2.setId(2L);
		specification2.setName("瓦数");
		specification2.setSequence(2);
		specification2.setCategoryId(1L);
		specificationMapper.insert(specification2);
		specificationItem1.setId(3L);
		specificationItem1.setName("50w");
		specificationItem1.setPid(2L);
		specificationItemMapper.insert(specificationItem1);
		specificationItem1.setId(4L);
		specificationItem1.setName("100w");
		specificationItem1.setPid(2L);
		specificationItemMapper.insert(specificationItem1);

		specification3 = new Specification();
		specification3.setId(3L);
		specification3.setName("颜色");
		specification3.setSequence(3);
		specification3.setCategoryId(2L);
		specificationMapper.insert(specification3);
		specificationItem1.setId(5L);
		specificationItem1.setName("白色");
		specificationItem1.setPid(3L);
		specificationItemMapper.insert(specificationItem1);
		specificationItem1.setId(6L);
		specificationItem1.setName("黑色");
		specificationItem1.setPid(3L);
		specificationItemMapper.insert(specificationItem1);

		queryObj = new Specification();
	}

	@After
	public void destory() {
		specificationMapper.deleteById(1L);
		specificationMapper.deleteById(2L);
		specificationMapper.deleteById(3L);
		specificationItemMapper.deleteById(1L);
		specificationItemMapper.deleteById(2L);
		specificationItemMapper.deleteById(3L);
		specificationItemMapper.deleteById(4L);
		specificationItemMapper.deleteById(5L);
		specificationItemMapper.deleteById(6L);
		queryObj = null;
	}

	/* 测试添加规格组，规格组名称在同分类下是否重名 */
	// 输入cid=1L,名称=口味 true
	@Test
	public void checkNameExistOnCategory_cid1_nameKouWei() {
		boolean expected = true;
		boolean actual = specificationService.checkNameOnCategory(1L, null, "口味");
		assertEquals(expected, actual);
	}

	// 输入cid=1L,名称=颜色 false
	@Test
	public void checkNameExistOnCategory_cid1_nameYanSe() {
		boolean expected = false;
		boolean actual = specificationService.checkNameOnCategory(1L, null, "颜色");
		assertEquals(expected, actual);
	}

	/* 测试修改规格组，规格组名称在同分类下是否重名 */
	// 输入id=1L,cid=1L,名称=口味 false
	@Test
	public void checkNameExistOnCategory_id1_cid1_nameKouWei() {
		boolean expected = false;
		boolean actual = specificationService.checkNameOnCategory(1L, 1L, "口味");
		assertEquals(expected, actual);
	}

	// 输入id=1L,cid=1L,名称=颜色 false
	@Test
	public void checkNameExistOnCategory_id1_cid1_nameYanSe() {
		boolean expected = false;
		boolean actual = specificationService.checkNameOnCategory(1L, 1L, "颜色");
		assertEquals(expected, actual);
	}

	// 输入id=1L,cid=1L,名称=瓦数 true
	@Test
	public void checkNameExistOnCategory_id1_cid1_nameWaShu() {
		boolean expected = true;
		boolean actual = specificationService.checkNameOnCategory(1L, 1L, "瓦数");
		assertEquals(expected, actual);
	}

	/* 测试添加规格项规格项在同规格组下是否重名 */
	// 输入id=1L(口味),规格项名=蓝莓味 false
	@Test
	public void checkNameExistOnSpecification_id1_optionLanMeiWei() {
		boolean expected = false;
		boolean actual = specificationItemService.checkNameOnSpecification(1L, null, "蓝莓味");
		assertEquals(expected, actual);
	}

	// 输入id=1L(口味),规格项名=草莓味 true
	@Test
	public void checkNameExistOnSpecification_id1_optionCaoMeiWei() {
		boolean expected = true;
		boolean actual = specificationItemService.checkNameOnSpecification(1L, null, "草莓味");
		assertEquals(expected, actual);
	}

	/* 测试修改规格项，规格项在同规格组下是否重名 */
	// 输入pid=1L(口味)，规格项id=1L,名称=草莓味 false
	@Test
	public void checkNameExistOnSpecification_pid1_id1_itemCaoMei() {
		boolean expected = false;
		boolean actual = specificationItemService.checkNameOnSpecification(1L, 1L, "草莓味");
		assertEquals(expected, actual);
	}

	// 输入pid=1L(口味)，规格项id=1L,名称=香草味 true
	@Test
	public void checkNameExistOnSpecification_pid1_id1_itemXiangCao() {
		boolean expected = true;
		boolean actual = specificationItemService.checkNameOnSpecification(1L, 1L, "香草味");
		assertEquals(expected, actual);
	}

	// 输入pid=1L(口味)，规格项id=1L,名称=蓝莓味 false
	@Test
	public void checkNameExistOnSpecification_pid1_id1_itemLanMei() {
		boolean expected = false;
		boolean actual = specificationItemService.checkNameOnSpecification(1L, 1L, "蓝莓味");
		assertEquals(expected, actual);
	}

	/* 测试通过商品分类，规格名查询规格 */
	// 都不输入查询 查出所有的3条记录
	@Test
	public void findSpecificationList_null() {
		int expected = 3;
		int actual = specificationService.findList(queryObj).size();
		assertEquals(expected, actual);
	}

	// 输入分类cid=1L， 查出两条记录
	@Test
	public void findSpecificationList_cid1() {
		queryObj.setCategoryId(1L);
		int expected = 2;
		int actual = specificationService.findList(queryObj).size();
		assertEquals(expected, actual);
	}

	// 输入规格名=口味 查出一条记录
	@Test
	public void findSpecificationList_nameKouWei() {
		queryObj.setName("口味");
		int expected = 1;
		int actual = specificationService.findList(queryObj).size();
		assertEquals(expected, actual);
	}

	// 输入规格名=口味，cid=1L 查出一条记录
	@Test
	public void findSpecificationList_cid1_nameKouWei() {
		queryObj.setCategoryId(1L);
		queryObj.setName("口味");
		int expected = 1;
		int actual = specificationService.findList(queryObj).size();
		assertEquals(expected, actual);
	}

	// 输入规格名=颜色，cid=1L 查出0条记录
	@Test
	public void findSpecificationList_cid1_nameYanSe() {
		queryObj.setCategoryId(1L);
		queryObj.setName("颜色");
		int expected = 0;
		int actual = specificationService.findList(queryObj).size();
		assertEquals(expected, actual);
	}

}
