package com.md.coupon.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.coupon.dao.PromotionPriceTagMapper;
import com.md.coupon.model.PromotionPriceTag;
import com.md.coupon.service.IPromotionPriceTagService;

@Service
public class PromotionPriceTagServiceImpl extends ServiceImpl<PromotionPriceTagMapper, PromotionPriceTag> implements IPromotionPriceTagService {

	@Resource
	PromotionPriceTagMapper promotionPriceTagMapper;

	@Override
	public List<Long> findPriceTagIds(Long promotionId) {
		List<Long> ids = new ArrayList<>();
		Wrapper<PromotionPriceTag> wrapper = new EntityWrapper<>();
		wrapper.eq("promotionId", promotionId);
		List<PromotionPriceTag> selectList = promotionPriceTagMapper.selectList(wrapper);
		for (PromotionPriceTag promotionPriceTag : selectList) {
			ids.add(promotionPriceTag.getPriceTagId());
		}
		return ids;
	}

	@Override
	public void deleteByPromotionId(Long promotionId) {
		Wrapper<PromotionPriceTag> wrapper = new EntityWrapper<>();
		wrapper.eq("promotionId", promotionId);
		promotionPriceTagMapper.delete(wrapper);
	}

	@Override
	public List<Long> findPromotionIds(Long priceTagId) {
		List<Long> ids = new ArrayList<>();
		Wrapper<PromotionPriceTag> wrapper = new EntityWrapper<>();
		wrapper.eq("priceTagId", priceTagId);
		List<PromotionPriceTag> selectList = promotionPriceTagMapper.selectList(wrapper);
		for (PromotionPriceTag promotionPriceTag : selectList) {
			ids.add(promotionPriceTag.getPromotionId());
		}
		return ids;
	}

}
