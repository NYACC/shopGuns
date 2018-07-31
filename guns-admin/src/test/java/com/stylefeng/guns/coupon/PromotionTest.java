package com.stylefeng.guns.coupon;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.md.coupon.dao.PromotionMapper;
import com.md.coupon.model.Promotion;
import com.md.coupon.service.IPromotionService;
import com.stylefeng.guns.base.BaseJunit;

public class PromotionTest extends BaseJunit {

	@Resource
	PromotionMapper promotionMapper;
	@Resource
	IPromotionService promotionService;

	Promotion promotion1;
	Promotion promotion2;
	Promotion promotion3;
	
	Promotion queryObj;

	@Before
	public void init() throws ParseException {
		promotion1 = new Promotion();
		promotion1.setId(1L);
		promotion1.setName("九折促销");
		promotion1.setStartTime(new Timestamp((
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-05-03 00:00:00")).getTime()));
		promotionMapper.insert(promotion1);

		promotion2 = new Promotion();
		promotion2.setId(2L);
		promotion2.setName("满1000减200促销");
		promotion2.setStartTime(new Timestamp(new Date(System.currentTimeMillis()+60*60*1000).getTime()));
		promotionMapper.insert(promotion2);

		promotion3 = new Promotion();
		promotion3.setId(3L);
		promotion3.setName("五折促销");
		promotionMapper.insert(promotion3);

		queryObj = new Promotion();
		
	}

	@After
	public void destroy() {
		promotionMapper.deleteById(promotion1.getId());
		promotionMapper.deleteById(promotion2.getId());
		promotionMapper.deleteById(promotion3.getId());

		promotion1 = null;
		promotion2 = null;
		promotion3 = null;
		queryObj = null;
	}

	// 根据名称查找促销的测试
	// 测试名称为100的促销
	@Test
	public void find_name1000() {
		queryObj.setName("1000");
		Integer expected = 1;
		Integer actual = promotionService.find(queryObj).size();
		assertEquals(expected, actual);
	}

	//测试是否可以操作
	//1L不可以操作
	@Test
	public void IsOperable_1L() {
		boolean expected = false;
		boolean actual = promotionService.isOperable(1L);
		assertEquals(expected, actual);
	}
	
	//测试是否可以操作
	//则2L可以操作
	@Test
	public void IsOperable_2L() {
		boolean expected = true;
		boolean actual = promotionService.isOperable(2L);
		assertEquals(expected, actual);
	}
}
