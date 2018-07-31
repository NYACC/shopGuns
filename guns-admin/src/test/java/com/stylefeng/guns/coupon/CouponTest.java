package com.stylefeng.guns.coupon;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.md.coupon.constant.CodeStatus;
import com.md.coupon.dao.CouponCodeMapper;
import com.md.coupon.dao.CouponMapper;
import com.md.coupon.model.Coupon;
import com.md.coupon.model.CouponCode;
import com.md.coupon.service.ICouponCodeService;
import com.md.coupon.service.ICouponService;
import com.stylefeng.guns.base.BaseJunit;

public class CouponTest extends BaseJunit {

	@Resource
	CouponMapper couponMapper;
	@Resource
	ICouponService couponService;
	@Resource
	CouponCodeMapper couponCodeMapper;
	@Resource
	ICouponCodeService couponCodeService;

	Coupon coupon1;
	Coupon coupon2;
	Coupon coupon3;
	Coupon queryObj;
	CouponCode couponCode1;
	CouponCode couponCode2;

	@Before
	public void init() {
		coupon1 = new Coupon();
		coupon1.setId(11L);
		coupon1.setName("满100减10");
		couponMapper.insert(coupon1);

		coupon2 = new Coupon();
		coupon2.setId(12L);
		coupon2.setName("满100减20");
		couponMapper.insert(coupon2);

		coupon3 = new Coupon();
		coupon3.setId(13L);
		coupon3.setName("满100减30");
		couponMapper.insert(coupon3);

		queryObj = new Coupon();

		couponCode1 = new CouponCode();
		couponCode1.setStatus(CodeStatus.CREATED.getCode());
		couponCode1.setCouponId(coupon1.getId());
		couponCodeMapper.insert(couponCode1);

		couponCode2 = new CouponCode();
		couponCode2.setStatus(CodeStatus.RECEIVED.getCode());
		couponCode2.setCouponId(coupon2.getId());
		couponCodeMapper.insert(couponCode2);
	}

	@After
	public void destroy() {
		couponMapper.deleteById(coupon1.getId());
		couponMapper.deleteById(coupon2.getId());
		couponMapper.deleteById(coupon3.getId());
		couponCodeMapper.deleteById(couponCode1.getId());
		couponCodeMapper.deleteById(couponCode2.getId());

		coupon1 = null;
		coupon2 = null;
		coupon3 = null;
		queryObj = null;
		couponCode1 = null;
		couponCode2 = null;
	}

	// 根据名称查找优惠卷的测试
	// 测试名称为100的优惠卷
	@Test
	public void find_name100() {
		queryObj.setName("100");
		Integer expected = 3;
		Integer actual = couponService.find(queryObj).size();
		assertEquals(expected, actual);
	}

	// 根据名称查找优惠卷的测试
	// 测试名称为30的优惠卷
	@Test
	public void find_name30() {
		queryObj.setName("30");
		Integer expected = 1;
		Integer actual = couponService.find(queryObj).size();
		assertEquals(expected, actual);
	}

	// 测试领取
	// 测试林区优惠卷1L，预期结果领取成功
	@Test
	public void receive_coupon1L_member1L() {
		Boolean expected = true;
		Boolean actual = couponCodeService.receive(1L, 1L);
		assertEquals(expected, actual);
	}

	// 测试领取
	// 测试林区优惠卷1L，预期结果领取失败
	@Test
	public void receive_coupon2L_member1L() {
		Boolean expected = false;
		Boolean actual = couponCodeService.receive(2L, 1L);
		assertEquals(expected, actual);
	}
}
