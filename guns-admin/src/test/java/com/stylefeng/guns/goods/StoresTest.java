package com.stylefeng.guns.goods;

import com.md.goods.dao.ShopMapper;
import com.md.goods.model.Shop;
import com.md.goods.service.IShopService;
import com.stylefeng.guns.base.BaseJunit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

public class StoresTest extends BaseJunit {
	@Resource
	private ShopMapper storesMapper;
	@Resource
	private IShopService storesService;
	Shop stores1;
	Shop stores2;
	Shop stores3;
	@Before
	public void init(){
		stores1=new Shop();
		stores1.setId(1L);
		stores1.setName("华林店");
		stores1.setAddress("xxx华林路");
		stores1.setNum(1);
		storesMapper.insert(stores1);

		stores2=new Shop();
		stores2.setId(2L);
		stores2.setName("学园店");
		stores2.setAddress("xxx学园路");
		stores2.setNum(2);
		storesMapper.insert(stores2);

		stores3=new Shop();
		stores3.setId(3L);
		stores3.setName("解放店");
		stores3.setAddress("xxx解放路");
		stores3.setNum(3);
		storesMapper.insert(stores3);

	}
	@After
	public void destroy(){
		storesMapper.deleteById(stores1.getId());
		storesMapper.deleteById(stores2.getId());
		storesMapper.deleteById(stores3.getId());
		stores1=null;
		stores2=null;
		stores3=null;
	}

	/**
	 * 判断是否重名
	 */
	@Test
	public void repeatName(){
		Shop stores=new Shop();
		String name="解放店";
		String address="xxx解放路";
		stores.setName(name);
		stores.setAddress(address);
		boolean flag=storesService.storesExist(stores);
		assertTrue(flag);

	}
}
