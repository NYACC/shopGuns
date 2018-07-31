package com.md.content.factory;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.md.content.dao.AdsPositionMapper;
import com.md.content.model.AdsPosition;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 广告工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class AdvertisingFactory {
	private AdsPositionMapper adsPositionMapper = SpringContextHolder.getBean(AdsPositionMapper.class);

	public static AdvertisingFactory me() {
		return SpringContextHolder.getBean(AdvertisingFactory.class);
	}

	public String getPositionName(Long positionId) {
		AdsPosition position = adsPositionMapper.selectById(positionId);
		return position.getName();
	}

}
