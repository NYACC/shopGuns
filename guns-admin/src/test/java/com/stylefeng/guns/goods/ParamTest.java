package com.stylefeng.guns.goods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.md.goods.factory.ParamFactory;
import com.md.goods.model.Category;
import com.md.goods.model.Param;
import com.md.goods.model.ParamItems;
import com.md.goods.service.IParamItemsService;
import com.md.goods.service.IParamService;
import com.stylefeng.guns.base.BaseJunit;

public class ParamTest extends BaseJunit{
	@Resource
	IParamService paramService;
	Param goodsParam1;
	Param goodsParam2;
	Param goodsParam3;

	Category goodsSort1;
	Category goodsSort2;
	Category goodsSort3;
	ParamItems item1;
	ParamItems item2;
	ParamItems item3;
	ParamItems item4;
	List<Param> paramList;
	@Resource
	IParamItemsService paramItemsService;
	@Before
	public void init(){
		goodsParam2=new Param();
		goodsParam2.setId(2L);
		goodsParam2.setName("镜头参数");
		item4=new ParamItems();
		item4.setId(4L);
		item4.setName("镜头结构");
		goodsParam2.setItemId(item4.getId()+"");

		goodsSort2=new Category();
		goodsSort2.setId(1L);
		goodsSort2.setName("相机");
		goodsParam2.setCategoryId(goodsSort2.getId());

		goodsParam1=new Param();
		goodsParam1.setId(1L);
		goodsParam1.setName("电源参数");
		item1=new ParamItems();
		item1.setId(1L);
		item1.setName("型号");
		item2=new ParamItems();
		item1.setId(2L);
		item2.setName("电池类型");
		item3=new ParamItems();
		item1.setId(3L);
		item3.setName("电池容量");
		goodsParam1.setItemId(item1.getId()+","+item2.getId()+","+item3.getId());

		goodsSort1=new Category();
		goodsSort1.setId(2L);
		goodsSort1.setName("手机");
		goodsParam1.setCategoryId(goodsSort1.getId());



		goodsParam3=new Param();
		goodsParam3.setId(3L);
		goodsParam3.setName("外观参数");

		goodsSort3=new Category();
		goodsSort3.setId(3L);
		goodsSort3.setName("电脑");

		goodsParam3.setCategoryId(goodsSort3.getId());

		paramList=new ArrayList<>();
		paramList.add(goodsParam1);
		paramList.add(goodsParam2);
		paramList.add(goodsParam3);
	}
	@After
	public void destroy(){
		goodsParam1=null;
		goodsParam2=null;
		goodsParam3=null;

		item1=null;
		item2=null;
		item3=null;
		item4=null;

		goodsSort1=null;
		goodsSort2=null;
		goodsSort3=null;
		paramList=null;
	}
	/**
	 * 查看参数组下是否有参数项
	 */
	@Test
	public void hasItemUnderTheParamGroup(){
		Long paramId=1L;
		boolean itemsFlag=paramService.existItems(paramId);
		assertTrue(itemsFlag);
	}
	/**
	 * 同分类下参数组名是否已经存在
	 */
	@Test
	public void existParamNameUnderTheSort(){
		Long sortId=1L;
		String paraName="镜头参数";
		boolean ParamFlag=paramService.existParamName(sortId,paraName);
		assertTrue(ParamFlag);
	}
	/**
	 * 同参数组下的参数项是否已经存在
	 */
	@Test
	public void existItemUnderTheParamGroup(){
		String itemName="电池类型";
		Long paramId=1L;
		boolean itemsFlag=paramService.existItemsName(itemName,paramId);
		assertTrue(itemsFlag);
	}
	/**
	 * 根据参数组名查询参数组
	 */
	@Test
	public void findParamGroupByParamGroupName(){
		String name;
		name = "参数";
		Param param;
		param = new Param();
		param.setName(name);
		List<Map<String, Object>> resultList;
		resultList = paramService.find(param);
		long expected=paramList.stream().filter(p ->p.getName().indexOf(name)!=-1).count();
		long actual=resultList.size();
		assertEquals(expected, actual);
	}
	/**
	 * 根据绑定的分类查询参数组
	 */
	@Test
	public void findParamGroupBySortId(){
		Long categoryId=2L;
		Param param=new Param();
		param.setCategoryId(categoryId);
		List<Map<String, Object>> resultList=paramService.find(param);
		long expected=paramList.stream().filter(p ->p.getCategoryId()==categoryId).count();
		long actual=resultList.size();
		assertEquals(expected, actual);
	}
	/**
	 * 添加参数组
	 */
	@Test
	public void addParam(){
		Param goodsParam=new Param();
		goodsParam.setId(4L);
		goodsParam.setName("cpu参数");
		goodsParam.setCategoryId(3L);
		List<ParamItems> items=new ArrayList<>();
		ParamItems item1=new ParamItems();
		item1.setId(5L);
		item1.setName("主频");
		ParamItems item2=new ParamItems();
		item2.setId(6L);
		item2.setName("外频");
		items.add(item1);
		items.add(item2);
		goodsParam.setItemId(ParamFactory.me().getItemsIds(items));
		paramService.insert(goodsParam);
		List<Map<String, Object>> resultList=paramService.find(goodsParam);
		long actual=resultList.size();
		assertTrue(actual==1);
	}
	/**
	 * 修改参数组
	 */
	@Test
	public void editParam(){
		Param goodsParam=new Param();
		goodsParam.setId(6L);
		goodsParam.setName("cpu参数");
		goodsParam.setCategoryId(3L);
		List<ParamItems> items=new ArrayList<>();
		ParamItems item1=new ParamItems();
		item1.setId(5L);
		item1.setName("接口");
		items.add(item1);
		goodsParam.setItemId(ParamFactory.me().getItemsIds(items));
		paramService.edit(goodsParam);
		Param param=paramService.findById(goodsParam.getId());
		String expected=goodsParam.toString();
		String actual=param.toString();
		assertEquals(expected, actual);
	}
	/**
	 * 删除参数
	 */
	@Test
	public void deleteParam(){
		Long paramId=4L;
		paramService.delete(paramId);
		Param condition=paramService.findById(paramId);
		assertTrue(condition==null);
	}

	/**
	 * 测试新建内容为空参数项是否能正确返回编号
	 */
	@Test
	public void createParamItem(){
		ParamItems paramItems=paramItemsService.insert();
		System.out.println(paramItems.getId());
	}
}
