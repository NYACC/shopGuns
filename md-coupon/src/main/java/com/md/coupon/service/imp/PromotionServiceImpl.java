package com.md.coupon.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.coupon.dao.PromotionMapper;
import com.md.coupon.dao.PromotionPriceTagMapper;
import com.md.coupon.model.Promotion;
import com.md.coupon.model.PromotionPriceTag;
import com.md.coupon.service.IPromotionService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class PromotionServiceImpl extends ServiceImpl<PromotionMapper, Promotion> implements IPromotionService {

	@Resource
	PromotionMapper promotionMapper;
	@Resource
	PromotionPriceTagMapper promotionPriceTagMapper;

	@Override
	public void add(Promotion promotion) {
		promotionMapper.insert(promotion);
	}

	@Override
	public List<Map<String, Object>> find(Promotion promotion) {
		Wrapper<Promotion> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(promotion.getName())) {
			wrapper.like("name", promotion.getName());
		}
		List<Map<String, Object>> promotions = promotionMapper.selectMaps(wrapper);
		return promotions;
	}

	@Override
	public Promotion getById(Long productId) {

		return promotionMapper.selectById(productId);
	}

	@Override
	public void update(Promotion promotion) {
		promotionMapper.updateById(promotion);
	}

	@Override
	public Boolean isOperable(Long id) {
		Promotion promotion = getById(id);
		if (promotion.getStartTime().getTime() < new Date().getTime()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void deleteById(Long promotionId) {
		promotionMapper.deleteById(promotionId);
	}

	@Override
	public void bindPriceTag(Long promotionId, Long priceTagId) {
		PromotionPriceTag promotionPriceTag = new PromotionPriceTag(promotionId, priceTagId);
		promotionPriceTagMapper.insert(promotionPriceTag);
	}

	@Override
	public void unbindPriceTag(Long promotionId, Long priceTagId) {
		Wrapper<PromotionPriceTag> wrapper = new EntityWrapper<>();
		wrapper.eq("priceTagId", priceTagId);
		wrapper.eq("promotionId", promotionId);
		promotionPriceTagMapper.delete(wrapper);
	}

	@Override
	public boolean allowBind(Long priceTagId, Promotion promotion) {
		// 获取该商品已参加过的促销为结束促销
		List<Promotion> findJionNoEnd = promotionMapper.findJionNoEnd(priceTagId);
		for (Promotion pro : findJionNoEnd) {
			if (!(promotion.getStartTime().compareTo(pro.getEndTime()) > 0
					|| promotion.getEndTime().compareTo(pro.getStartTime()) < 0)) {
				return false;
			}
		}
		return true;
	}

}
