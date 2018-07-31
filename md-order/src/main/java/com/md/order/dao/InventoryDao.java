package com.md.order.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 库存单dao
 *
 * @author fengshuonan
 * @date 2017年2月17日20:28:58
 */
public interface InventoryDao {
        /**
         * 获取最大sn码
         * @param currentTime
         * @return
         */
        String  getMaxSn(@Param("currentTime") String currentTime);
}
