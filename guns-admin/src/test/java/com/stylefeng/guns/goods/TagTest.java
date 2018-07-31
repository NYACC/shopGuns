package com.stylefeng.guns.goods;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.md.goods.dao.TagMapper;
import com.md.goods.model.Tag;
import com.md.goods.service.imp.TagServiceImpl;
import com.stylefeng.guns.base.BaseJunit;

public class TagTest extends BaseJunit{
	@Resource
	TagMapper tagMapper;
	@Resource
	TagServiceImpl tagService;
	private Tag tag1;
	private Tag tag2;
	
	@Before
	public void init(){
		tag1=new Tag();
		tag1.setId(1L);
		tag1.setName("大减价");
		tag1.setSequence(1);
		tagMapper.insertAllColumn(tag1);
		
		tag2=new Tag();
		tag2.setId(2L);
		tag2.setName("买一送一");
		tag2.setSequence(2);
		tagMapper.insertAllColumn(tag2);
	}
	
	@After
	public void destory(){
		tagMapper.deleteById(1L);
		tagMapper.deleteById(2L);
	}
	
	
	//添加时：输入名字“买一送一”----存在
	@Test
	public void add_MaiYiSongYi(){
		boolean expected=true;
		boolean actual=tagService.checkNameExist(null,"买一送一");
		assertEquals(expected, actual);
	}
	
	//添加时：输入名字“买一送二”-----不存在
	@Test
	public void add_MaiYiSongEr(){
		boolean expected=false;
		boolean actual=tagService.checkNameExist(null,"买一送二");
		assertEquals(expected, actual);
	}
	
	//修改时：输入id=1,名称=大减价。         
	@Test
	public void edit_id1_DaJianJia(){
		boolean expected=false;
		boolean actual=tagService.checkNameExist(1L,"大减价");
		assertEquals(expected, actual);
	}
	
	//修改时，输入id=1,名称=买一送一        
	@Test
	public void edit_id1_MaiYiSongYi(){
		boolean expected=true;
		boolean actual=tagService.checkNameExist(1L,"买一送一");
		assertEquals(expected, actual);
	}
	
	//修改时，输入id=1,名称=买一送二() 
	@Test
	public void edit_id1_MaiYiSongEr(){
		boolean expected=false;
		boolean actual=tagService.checkNameExist(1L,"买一送二");
		assertEquals(expected, actual);
	}
}
