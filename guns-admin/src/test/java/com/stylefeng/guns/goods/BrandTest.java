package com.stylefeng.guns.goods;

import com.md.goods.dao.BrandMapper;
import com.md.goods.model.Brand;
import com.md.goods.service.IBrandService;
import com.stylefeng.guns.base.BaseJunit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class BrandTest extends BaseJunit {
	
	@Resource
	IBrandService brandService;
	@Resource
	BrandMapper brandMapper;

	Brand brand1;
	Brand brand2;
	Brand brand3;
	
	List<Brand> brandList;
	@Before
	public void init(){
		brandList = new ArrayList<>();
		
		brand1=new Brand();
		brand1.setName("百雀羚");
		brand1.setIntroduce("测试介绍");
		brand1.setAvatar("测试路径");
		brand1.setNum(1);
		brand1.setStatus(0);
		brandList.add(brand1);
		
		brand2=new Brand();
		brand2.setName("小米");
		brand2.setIntroduce("测试介绍");
		brand2.setAvatar("测试路径");
		brand2.setNum(2);
		brand2.setStatus(0);
		brandList.add(brand2);
		
		brand3=new Brand();
		brand3.setName("森马");
		brand3.setIntroduce("测试介绍");
		brand3.setAvatar("测试路径");
		brand3.setNum(3);
		brand3.setStatus(0);
		brandList.add(brand3);
	}
	@After
	public void destroy(){
		brandList = null;
		
		brandMapper.deleteById(brand1.getId());
		brandMapper.deleteById(brand2.getId());
		brandMapper.deleteById(brand3.getId());
		brand1=null;
		brand2=null;
		brand3=null;
		
	}
	
	/**
	 * 查询所有品牌的名称、品牌介绍、品牌logo、排序、停用状态
	 */
	@Test
	public void findAllBrand(){
		Brand brand=new Brand();
		List<Map<String, Object>> resultList = brandService.find(brand);
		int expected = brandList.size();
		int actual = resultList.size();
		assertEquals(expected, actual);
	}
	
	/**
	 * 查询所有品牌的名称、品牌介绍、品牌logo、排序、停用状态
	 */
	@Test
	public void findBrandByName(){
		String searchName = "小米";
		
		Brand brand=new Brand();
		brand.setName(searchName);
		List<Map<String, Object>> resultList = brandService.find(brand);
		long expected = brandList.stream().filter(b -> (b.getName().indexOf(searchName)>=0)).count();
		long actual = resultList.size();
		
		assertEquals(expected, actual);
	}
	/**
	 * 查看所有品牌列表,传入null参数
	 */
	 @Test
	 public void viewBrand(){
		 List<Map<String, Object>> resultList=brandService.find(null);
		 int expected=brandList.size();
		 int actual=resultList.size();
		 assertEquals(expected, actual);
	 }
	 /**
	  * 测试品牌名是否存在
	  */
	 @Test
	 public void existBrandTrueOrFalse(){
		String name="小米";		
		boolean existFlag=brandService.NameExist(name);		
		assertTrue(existFlag);
	 }

}
