package com.md.order.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.SystemUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.order.dao.RefundApplyMapper;
import com.md.order.model.Order;
import com.md.order.model.RefundApply;
import com.md.order.service.IRefundApplyService;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
@Transactional
public class RefundApplyServiceImpl extends ServiceImpl<RefundApplyMapper, RefundApply> implements IRefundApplyService {

	@Resource
	RefundApplyMapper refundApplyMapper;

	@Override
	public boolean add(Order order, String applyWhy) {
		// TODO 自动生成的方法存根
		Wrapper<RefundApply> wrapper = new EntityWrapper<>();
		wrapper.eq("orderId", order.getId());
		wrapper.eq("status", 7);
		if(refundApplyMapper.selectList(wrapper).size() == 0){
			RefundApply refundApply =  new RefundApply();
			refundApply.setOrderId(order.getId());
			refundApply.setStatus(7);
			refundApply.setMemberId(order.getMemberId());
			refundApply.setApplyWhy(applyWhy);
			refundApply.setCreateTime(DateUtil.getTime());
			refundApplyMapper.insert(refundApply);
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> find(RefundApply refundApply) {
		// TODO 自动生成的方法存根
		Wrapper<RefundApply> wrapper = new EntityWrapper<>();
		if(ToolUtil.isNotEmpty(refundApply)){
			if(ToolUtil.isNotEmpty(refundApply.getOrderId())){
				wrapper.eq("orderId", refundApply.getOrderId());
			}
			if(ToolUtil.isNotEmpty(refundApply.getStatus())){
				wrapper.eq("status", refundApply.getStatus());
			}
		}
		wrapper.orderBy("createTime");
		return refundApplyMapper.selectMaps(wrapper);
	}


}
