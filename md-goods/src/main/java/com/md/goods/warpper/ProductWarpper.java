package com.md.goods.warpper;

import java.util.Map;

import com.md.goods.factory.GoodsFactory;
import com.md.goods.factory.UploadFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

/**
 * 规格商品列表的包装类
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class ProductWarpper extends BaseControllerWarpper {

    public ProductWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
   	 	 map.put("goodsName",GoodsFactory.me().getNameById((Long)map.get("goodsId"))+map.get("name"));
    	 map.put("id", (map.get("id")).toString());
    	 map.put("goodsId", (map.get("goodsId")).toString());
    	 map.put("imageUrl", UploadFactory.me().getUploadFileUrl((Long)map.get("image")));
    	 map.put("image", (map.get("image")==null)?"":map.get("image").toString());
    }

}
