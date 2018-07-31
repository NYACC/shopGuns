package com.md.member.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.member.dao.DenominationMapper;
import com.md.member.model.Denomination;
import com.md.member.service.IDenominationService;

@Service
public class DenominationServiceImpl extends ServiceImpl<DenominationMapper, Denomination>
		implements IDenominationService {

	@Resource
	DenominationMapper denominationMapper;

	@Override
	public List<Map<String, Object>> list() {
		Wrapper<Denomination> wrapper = new EntityWrapper<>();
		wrapper.orderBy("value", true);
		List<Map<String, Object>> selectMaps = denominationMapper.selectMaps(wrapper);
		return selectMaps;
	}

	@Override
	public void add(Denomination denomination) {
		denominationMapper.insert(denomination);
	}

	@Override
	public Denomination getById(Long id) {
		Denomination denomination = denominationMapper.selectById(id);
		return denomination;
	}

	@Override
	public void update(Denomination denomination) {
		denominationMapper.updateById(denomination);
	}

	@Override
	public void delete(Long denominationId) {
		denominationMapper.deleteById(denominationId);
	}

}
