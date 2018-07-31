package com.md.content.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.content.dao.AdsPositionMapper;
import com.md.content.model.AdsPosition;
import com.md.content.service.IAdsPositionService;

@Service
public class AdsPositionServiceImpl extends ServiceImpl<AdsPositionMapper, AdsPosition> implements IAdsPositionService {

	@Resource
	AdsPositionMapper adsPositionMapper;

	@Override
	public List<Map<String, Object>> list() {
		Wrapper<AdsPosition> wrapper = new EntityWrapper<>();
		List<Map<String, Object>> selectMaps = adsPositionMapper.selectMaps(wrapper);
		return selectMaps;
	}

	@Override
	public void add(AdsPosition adsPosition) {
		adsPositionMapper.insert(adsPosition);
	}

	@Override
	public AdsPosition getById(Long adsPositionId) {
		return adsPositionMapper.selectById(adsPositionId);
	}

	@Override
	public void update(AdsPosition adsPosition) {
		adsPositionMapper.updateById(adsPosition);
	}

	@Override
	public void deleteById(Long adsPositionId) {
		adsPositionMapper.deleteById(adsPositionId);
	}

}
