package com.md.order.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.md.order.model.Evaluation;

public interface IEvaluationService extends IService<Evaluation> {
	
	/**
	 * 获取评价列表
	 * @param evaluation
	 * @return
	 */
	List<Map<String, Object>> findList(Evaluation evaluation);
	
	/**
	 * 获取分页评价列表
	 * @param goodsId
	 * @param shopId
	 * @param index
	 * @return
	 */
	List<Evaluation> findListByPage(Long goodsId,Long shopId,Integer index);
}
