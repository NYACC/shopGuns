package com.md.coupon.service.imp;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.coupon.constant.CodeStatus;
import com.md.coupon.dao.CouponCodeMapper;
import com.md.coupon.model.Coupon;
import com.md.coupon.model.CouponCode;
import com.md.coupon.service.ICouponCodeService;

@Service
public class CouponCodeServiceImpl extends ServiceImpl<CouponCodeMapper, CouponCode> implements ICouponCodeService {

	@Resource
	CouponCodeMapper couponCodeMapper;

	@Override
	public Boolean isOperable(Long couponId) {
		Wrapper<CouponCode> wrapper = new EntityWrapper<>();
		wrapper.eq("couponId", couponId);
		Integer number = couponCodeMapper.selectCount(wrapper);
		if (number > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean receive(Long couponId, Long memberId) {
		Wrapper<CouponCode> wrapper = new EntityWrapper<>();
		if (isReceive(couponId, memberId)) {
			wrapper.eq("couponId", couponId);
			wrapper.eq("status", CodeStatus.CREATED.getCode());
			List<CouponCode> coupons = couponCodeMapper.selectList(wrapper);
			// 判断是否还有"未领取"优惠卷
			if (coupons.size() > 0) {
				CouponCode couponCode = coupons.get(0);
				couponCode.setMemberId(memberId);
				couponCode.setStatus(CodeStatus.RECEIVED.getCode());
				couponCodeMapper.updateById(couponCode);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void produce(CouponCode couponCode) {
		couponCode.setCreateTime(new Timestamp(new Date().getTime()));
		couponCodeMapper.insert(couponCode);
	}

	@Override
	public List<Map<String, Object>> create(Coupon coupon, Integer quantity) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < quantity; i++) {
			CouponCode couponCode = new CouponCode();
			couponCode.setCouponId(coupon.getId());
			couponCode.setStatus(CodeStatus.RECEIVED.getCode());
			couponCode.setCode(String.valueOf(new Date().getTime()));
			couponCode.setCreateTime(new Timestamp(new Date().getTime()));
			couponCodeMapper.insert(couponCode);
			Map<String, Object> couponCodeMap = this.object2Map(couponCode);
			list.add(couponCodeMap);
		}
		return list;
	}

	public Map<String, Object> object2Map(Object obj) {
		Map<String, Object> map = new HashMap<>();
		if (obj == null) {
			return map;
		}
		@SuppressWarnings("rawtypes")
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Boolean isReceive(Long couponId, Long memberId) {
		Wrapper<CouponCode> wrapper = new EntityWrapper<>();
		wrapper.eq("couponId", couponId);
		wrapper.eq("memberId", memberId);
		wrapper.eq("status", CodeStatus.RECEIVED.getCode());
		Integer number = couponCodeMapper.selectCount(wrapper);
		if (number > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public CouponCode getByCode(String code) {
		Wrapper<CouponCode> wrapper = new EntityWrapper<>();
		wrapper.eq("code", code);
		List<CouponCode> couponCodes = couponCodeMapper.selectList(wrapper);
		if (couponCodes.size() > 0) {
			return couponCodes.get(0);
		}else{
			return null;
		}
	}
}
