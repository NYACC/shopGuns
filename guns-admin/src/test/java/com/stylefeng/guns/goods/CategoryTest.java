package com.stylefeng.guns.goods;

import com.md.goods.dao.CategoryMapper;
import com.md.goods.model.Category;
import com.md.goods.service.ICategoryService;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.common.constant.state.CategoryStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryTest extends BaseJunit {

	@Resource
	CategoryMapper categoryMapper;
	@Resource
	ICategoryService categoryService;
	private Category category1;
	private Category category2;
	private Category category3;
	private Category category4;
	private Category category5;

	@Before
	public void init() {
		category1 = new Category();
		category1.setName("数码产品");
		category1.setStatus(CategoryStatus.ENABLE.getCode());
		categoryMapper.insert(category1);

		category2 = new Category();
		category2.setName("电视机");
		category2.setPid(1L);
		category2.setStatus(CategoryStatus.ENABLE.getCode());
		categoryMapper.insert(category2);

		category3 = new Category();
		category3.setName("相机");
		category3.setPid(1L);
		category3.setStatus(CategoryStatus.ENABLE.getCode());
		categoryMapper.insert(category3);

		category4 = new Category();
		category4.setName("单反");
		category4.setPid(3L);
		category4.setStatus(CategoryStatus.ENABLE.getCode());
		categoryMapper.insert(category4);

		category5 = new Category();
		category5.setName("海鲜");
		category5.setStatus(CategoryStatus.ENABLE.getCode());
		categoryMapper.insert(category5);

	}

	@After
	public void destory() {
		categoryMapper.deleteById(category1.getId());
		categoryMapper.deleteById(category2.getId());
		categoryMapper.deleteById(category3.getId());
		categoryMapper.deleteById(category4.getId());
		categoryMapper.deleteById(category5.getId());
		category1 = null;
		category2 = null;
		category3 = null;
		category4 = null;
		category5 = null;
	}

	// 测试：检验添加的时，分类的名称是否重名
	// 输入分类名称=相机 true
	@Test
	public void checkNameExist_nameXiangJi() {
		Boolean expected = true;
		Boolean actual = categoryService.checkNameExist(null, "相机");
		assertEquals(expected, actual);
	}

	// 输入分类名称="鱼"
	@Test
	public void checkNameExist_nameYu() {
		Boolean expected = false;
		Boolean actual = categoryService.checkNameExist(null, "鱼");
		assertEquals(expected, actual);
	}

	// 测试：检验修改时，分类的名称是否重名
	// 输入 id=3 分类名称=相机 false
	@Test
	public void checkNameExist_id3_nameXiangJi() {
		Boolean expected = false;
		Boolean actual = categoryService.checkNameExist(category3.getId(), "相机");
		assertEquals(expected, actual);
	}

	// 输入 id=2 分类名称=相机 true
	@Test
	public void checkNameExist_id2_nameXiangJi() {
		Boolean expected = true;
		Boolean actual = categoryService.checkNameExist(category2.getId(), "相机");
		assertEquals(expected, actual);
	}

	// 输入 id=2 分类名称=鱼 false
	@Test
	public void checkNameExist_id2_nameYu() {
		Boolean expected = false;
		Boolean actual = categoryService.checkNameExist(category2.getId(), "鱼");
		assertEquals(expected, actual);
	}

	// 测试查询子分类
	// 输入 id=1 查出2条数据
	@Test
	public void findSonList_id1() {
		Integer expected = 2;
		Integer actual = categoryService.findSonList(category1.getId()).size();
		assertEquals(expected, actual);
	}

	// 输入 id=2 查出0条数据
	@Test
	public void findSonList_id2() {
		Integer expected = 0;
		Integer actual = categoryService.findSonList(category2.getId()).size();
		assertEquals(expected, actual);
	}
	
	//输入id=0 查出2条数据
	@Test
	public void findSonList_id0() {
		Integer expected = 2;
		Integer actual = categoryService.findSonList(0L).size();
		assertEquals(expected, actual);
	}

	// 测试：停用分类
	// 输入 id=1
	@Test
	public void disable_id1() {
		Integer expected = CategoryStatus.DISABLE.getCode();
		categoryService.disable(category1.getId());
		Integer actual = categoryMapper.selectById(category1.getId()).getStatus();
		assertEquals(expected, actual);
	}

	// 测试:停用分类后，下面的子分类是否也都停用了
	// 输入id=1
	@Test
	public void disablePosterity_id1() {
		Integer expected = CategoryStatus.DISABLE.getCode();
		categoryService.disable(category1.getId());
		Integer posterity1 = categoryMapper.selectById(category2.getId()).getStatus();
		assertEquals(expected, posterity1);
		Integer posterity2 = categoryMapper.selectById(category3.getId()).getStatus();
		assertEquals(expected, posterity2);
		Integer posterity3 = categoryMapper.selectById(category4.getId()).getStatus();
		assertEquals(expected, posterity3);
	}

	// 测试：停用分类
	// 输入 id=1
	@Test
	public void enable_id1() {
		Integer expected = CategoryStatus.ENABLE.getCode();
		categoryService.enable(category1.getId());
		Integer actual = categoryMapper.selectById(category1.getId()).getStatus();
		assertEquals(expected, actual);
	}

	// 测试:停用分类后，下面的子分类是否也都停用了
	// 输入id=1
	@Test
	public void enablePosterity_id1() {
		Integer expected = CategoryStatus.ENABLE.getCode();
		categoryService.disable(category1.getId());
		categoryService.enable(category1.getId());
		Integer posterity1 = categoryMapper.selectById(category2.getId()).getStatus();
		assertEquals(expected, posterity1);
		Integer posterity2 = categoryMapper.selectById(category3.getId()).getStatus();
		assertEquals(expected, posterity2);
		Integer posterity3 = categoryMapper.selectById(category4.getId()).getStatus();
		assertEquals(expected, posterity3);
	}
}
