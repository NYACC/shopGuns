package com.md.delivery.factory;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.md.delivery.dao.AreaMapper;
import com.md.delivery.model.Area;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class DeliveryCostFactory {
	private AreaMapper areaMapper = SpringContextHolder.getBean(AreaMapper.class);

	public static DeliveryCostFactory me() {
		return SpringContextHolder.getBean(DeliveryCostFactory.class);
	}

    public String getAreaName(Long areaId){
    	Area area = areaMapper.selectById(areaId);
    	return area.getFull_name();
    }
}
