package com.md.delivery.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.delivery.dao.AreaMapper;
import com.md.delivery.model.Area;
import com.md.delivery.service.IAreaService;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

	@Resource
	AreaMapper areaMapper;

	@Override
	public List<Area> countyList() {
		Wrapper<Area> wrapper = new EntityWrapper<>();
		wrapper.eq("grade", 2);
		List<Area> areas = areaMapper.selectList(wrapper);
		return areas;
	}

	@Override
	public List<Area> getProvince() {
		Wrapper<Area> wrapper = new EntityWrapper<>();
		wrapper.eq("grade", 0);
		wrapper.orderBy("orders", true);
		List<Area> areas = areaMapper.selectList(wrapper);
		return areas;
	}

	@Override
	public List<Area> getCity(Long province) {
		Wrapper<Area> wrapper = new EntityWrapper<>();
		wrapper.eq("grade", 1);
		wrapper.eq("parent", province);
		wrapper.orderBy("orders", true);
		List<Area> areas = areaMapper.selectList(wrapper);
		return areas;
	}

	@Override
	public List<Area> getCounty(Long city) {
		Wrapper<Area> wrapper = new EntityWrapper<>();
		wrapper.eq("grade", 2);
		wrapper.eq("parent", city);
		wrapper.orderBy("orders", true);
		List<Area> areas = areaMapper.selectList(wrapper);
		return areas;
	}

	@Override
	public List<Long> findCountyIds(Long province, Long city, Long county) {
		List<Long> ids = new ArrayList<>();
		// 是否有选择区县
		if (ToolUtil.isNotEmpty(county)) {
			ids.add(county);
		} else {
			// 是否有选择市
			if (ToolUtil.isNotEmpty(city)) {
				Wrapper<Area> wrapper = new EntityWrapper<>();
				wrapper.eq("parent", city);
				wrapper.orderBy("orders", true);
				List<Area> areas = areaMapper.selectList(wrapper);
				for (Area area : areas) {
					ids.add(area.getId());
				}
			} else {
				// 是否有选择省
				if (ToolUtil.isNotEmpty(province)) {
					List<Long> cityIds = new ArrayList<>();
					Wrapper<Area> wrapper = new EntityWrapper<>();
					wrapper.eq("parent", province);
					wrapper.orderBy("orders", true);
					List<Area> areas = areaMapper.selectList(wrapper);
					for (Area area : areas) {
						cityIds.add(area.getId());
					}
					Wrapper<Area> wrapper1 = new EntityWrapper<>();
					wrapper1.in("parent", cityIds);
					wrapper1.orderBy("orders", true);
					List<Area> countys = areaMapper.selectList(wrapper1);
					for (Area area : countys) {
						ids.add(area.getId());
					}
				}
			}
		}
		return ids;
	}

	@Override
	public List<Map<String, Object>> getAllCounty() {
		Wrapper<Area> wrapper = new EntityWrapper<>();
		wrapper.eq("grade", 2);
		List<Map<String, Object>> areas = areaMapper.selectMaps(wrapper);
		return areas;
	}

	@Override
	public Area getByFullName(String fullName) {
		Wrapper<Area> wrapper = new EntityWrapper<>();
		wrapper.eq("full_name", fullName);
		List<Area> areas = areaMapper.selectList(wrapper);
		if (areas.size()>0) {
			return areas.get(0);
		}else{
			return null;
		}
		
	}

}
