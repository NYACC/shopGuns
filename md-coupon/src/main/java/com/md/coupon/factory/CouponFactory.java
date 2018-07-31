package com.md.coupon.factory;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.md.coupon.constant.CodeStatus;
import com.md.coupon.dao.CouponCodeMapper;
import com.md.coupon.model.CouponCode;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 优惠卷创建工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class CouponFactory {
	private CouponCodeMapper couponCodeMapper = SpringContextHolder.getBean(CouponCodeMapper.class);

	public static CouponFactory me() {
		return SpringContextHolder.getBean(CouponFactory.class);
	}

	/**
	 * 查找某优惠卷下的某状态优惠码个数
	 * @param couponId
	 * @param codeStatus
	 * @return
	 */
	public Integer getNumByStatus(Long couponId,CodeStatus codeStatus) {
		Wrapper<CouponCode> wrapper =new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(couponId)) {
			wrapper.eq("couponId", couponId);
			if(ToolUtil.isNotEmpty(codeStatus)){
				wrapper.eq("status", codeStatus.getCode());
			}
			Integer number = couponCodeMapper.selectCount(wrapper);
			return number;
		}
		return null;
	}

}
