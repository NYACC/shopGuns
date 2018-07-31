package com.md.goods.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.md.goods.model.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 获取最大sn码
     * @param currentTime
     * @return
     */
    String  getMaxSn(@Param("currentTime")String currentTime);

    /**
     * 促销关系绑定获取商品列表
     * @param shopIds
     * @param categoryId
     * @param brandId
     * @param goodsName
     */
    List<Map<String,Object>> goodsList(@Param("shopIds")String shopIds, @Param("categoryId")Long categoryId, @Param("brandId") Long brandId, @Param("goodsName")String goodsName);

    /**
     * 获取绑定的价格签
     * @param ids
     * @return
     */
    List<Map<String, Object>> findBindPriceTag(@Param("ids")List<Long> ids);
}
