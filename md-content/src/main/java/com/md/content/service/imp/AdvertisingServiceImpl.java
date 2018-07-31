package com.md.content.service.imp;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.content.dao.AdvertisingMapper;
import com.md.content.model.Advertising;
import com.md.content.service.IAdvertisingService;
import com.stylefeng.guns.core.support.StrKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class AdvertisingServiceImpl extends ServiceImpl<AdvertisingMapper, Advertising> implements IAdvertisingService {

	@Resource
	AdvertisingMapper advertisingMapper;

	@Override
	public List<Map<String, Object>> findList(Advertising advertising) {
		Wrapper<Advertising> wrapper = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(advertising)) {
			if (ToolUtil.isNotEmpty(advertising.getPositionId())) {
				wrapper.eq("positionId", advertising.getPositionId());
			}
		}
		wrapper.orderBy("sequence", true);
		List<Map<String, Object>> selectMaps = advertisingMapper.selectMaps(wrapper);
		return selectMaps;
	}

	@Override
	public void add(Advertising advertising) {
		advertisingMapper.insert(advertising);
	}

	@Override
	public Advertising getById(Long advertisingId) {
		return advertisingMapper.selectById(advertisingId);
	}

	@Override
	public void update(Advertising advertising) {
		advertisingMapper.updateById(advertising);
	}

	@Override
	public void deleteById(Long advertisingId) {
		advertisingMapper.deleteById(advertisingId);
	}

	@Override
	public void deletByPosition(Long adsPositionId) {
		Wrapper<Advertising> wrapper = new EntityWrapper<>();
		wrapper.eq("positionId", adsPositionId);
		advertisingMapper.delete(wrapper);
	}

	@Override
	public void bindPriceTag(Long advertisingId, Long priceTagId) {
		Advertising advertising = this.getById(advertisingId);
		if (ToolUtil.isNotEmpty(advertising.getPriceTagIds())) {
			advertising.setPriceTagIds(advertising.getPriceTagIds()+","+priceTagId);
		}else{
			advertising.setPriceTagIds(priceTagId.toString());
		}
		this.update(advertising);
	}

	@Override
	public void unbindPriceTag(Long advertisingId, Long priceTagId) {
		Set<Long> tags = new HashSet<>();
		Advertising advertising = this.getById(advertisingId);
		if (ToolUtil.isNotEmpty(advertising.getPriceTagIds())) {
			for (Long tagId : Convert.toLongArray(true, Convert.toStrArray(",", advertising.getPriceTagIds()))) {
				tags.add(tagId);
			}
		}
		tags.remove(priceTagId);
		StringBuilder sb = new StringBuilder();
		for (Long item : tags) {
			sb.append(item).append(",");
		}
		advertising.setPriceTagIds(StrKit.removeSuffix(sb.toString(), ","));
		this.update(advertising);
	}

}
