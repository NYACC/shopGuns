package com.md.coupon.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.coupon.constant.CouponModel;
import com.md.coupon.dao.CouponMapper;
import com.md.coupon.model.Coupon;
import com.md.coupon.service.ICouponService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

	@Resource
	CouponMapper couponMapper;

	@Override
	public List<Map<String, Object>> find(Coupon coupon) {
		Wrapper<Coupon> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(coupon)) {
			if (ToolUtil.isNotEmpty(coupon.getName())) {
				wrapper.like("name", coupon.getName());
			}
			if (ToolUtil.isNotEmpty(coupon.getShopId())) {
				wrapper.eq("shopId", coupon.getShopId());
			}
			if (ToolUtil.isNotEmpty(coupon.getModel())) {
				wrapper.eq("model", coupon.getModel());
			}
		}
		List<Map<String, Object>> selectMaps = couponMapper.selectMaps(wrapper);
		return selectMaps;
	}

	@Override
	public void add(Coupon coupon) {
		couponMapper.insert(coupon);
	}

	@Override
	public Coupon getById(Long couponId) {
		Coupon coupon = couponMapper.selectById(couponId);
		return coupon;
	}

	@Override
	public void update(Coupon coupon) {
		couponMapper.updateById(coupon);
	}

	@Override
	public void deleteById(Long couponId) {
		couponMapper.deleteById(couponId);
	}

	@Override
	public List<Map<String, Object>> getList(Long shopId) {
		Wrapper<Coupon> wrapper = new EntityWrapper<>();
		wrapper.eq("shopId", shopId);
		wrapper.eq("model", CouponModel.RECEIVECOUPON.getCode());
		wrapper.ge("receiveEnd", new Date()); // >=
		wrapper.le("receiveStart", new Date()); // <=
		return couponMapper.selectMaps(wrapper);
	}

	@Override
	public List<Map<String, Object>> getMyCouponList(Long memberId) {
		Wrapper<Coupon> wrapper = new EntityWrapper<>();
		wrapper.where(
				" useEnd >= NOW() AND id IN ( SELECT couponId FROM shop_coupon_code WHERE STATUS = 1 AND memberId = "
						+ memberId + ")");
		return couponMapper.selectMaps(wrapper);
	}

}
