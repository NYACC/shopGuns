package com.md.coupon.factory;

import java.sql.Timestamp;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.md.coupon.dao.CouponMapper;
import com.md.coupon.model.Coupon;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 优惠码工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class CouponCodeFactory {
	private CouponMapper couponMapper = SpringContextHolder.getBean(CouponMapper.class);

	public static CouponCodeFactory me() {
		return SpringContextHolder.getBean(CouponCodeFactory.class);
	}

	/**
	 * 获取优惠卷名称
	 * 
	 * @param couponId
	 * @return
	 */
	public String getName(Long couponId) {
		Coupon coupon = couponMapper.selectById(couponId);
		return coupon.getName();
	}

	/**
	 * 获取开始使用的时间
	 * 
	 * @param couponId
	 * @return
	 */
	public Timestamp getUseStart(Long couponId) {
		Coupon coupon = couponMapper.selectById(couponId);
		return coupon.getUseStart();
	}

	/**
	 * 获取结束使用的时间
	 * 
	 * @param couponId
	 * @return
	 */
	public Timestamp getUseEnd(Long couponId) {
		Coupon coupon = couponMapper.selectById(couponId);
		return coupon.getUseEnd();
	}

	/**
	 * 获取优惠内容
	 * 
	 * @param couponId
	 * @return
	 */
	public String getContent(Long couponId) {
		Coupon coupon = couponMapper.selectById(couponId);
		String content = "满" + coupon.getFulfil();
		content += "减" + coupon.getReduce();
		return content;
	}

	public String getRemark(Long couponId) {
		Coupon coupon = couponMapper.selectById(couponId);
		return coupon.getRemark();
	}
}
