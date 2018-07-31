package com.stylefeng.guns.common.constant.dictmap.order;

import com.stylefeng.guns.common.constant.dictmap.base.AbstractDictMap;

/**
 * 用户的字典
 *
 * @author fengshuonan
 * @date 2017-05-06 15:01
 */
public class OrderDict extends AbstractDictMap {

    @Override
    public void init() {
        put("sn","编号");
        put("consigneeName", "收货人");
        put("phoneNum", "电话");
        put("address", "地址");
    }

    @Override
    protected void initBeWrapped() {
    }
}
