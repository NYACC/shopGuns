package com.md.delivery.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.md.delivery.model.DeliveryCost;

public interface DeliveryCostMapper extends BaseMapper<DeliveryCost> {
	/**
	 * 获取配送费用列表
	 * 
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> findList(@Param("modeId") Long modeId, @Param("areaIds") List<Long> areaIds,
			@Param("deliveryArea") List<Long> deliveryArea, @Param("isdelivery") Boolean isdelivery);
}
