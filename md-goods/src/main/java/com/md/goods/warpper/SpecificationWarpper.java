package com.md.goods.warpper;

import com.md.goods.factory.CategoryFactory;
import com.md.goods.factory.SpecificationFactory;
import com.md.goods.model.SpecificationItem;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.ToolUtil;

import java.util.List;
import java.util.Map;

/**
 * 规格列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class SpecificationWarpper extends BaseControllerWarpper {

    public SpecificationWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("categoryName", CategoryFactory.me().getCategoryName((Long) map.get("categoryId")));
        StringBuffer items = new StringBuffer();
        Long id = (Long) map.get("id");
        List<SpecificationItem> specificationItems = SpecificationFactory.me().findSpecification(id);
        if (specificationItems!=null) {
			for (SpecificationItem specificationItem : specificationItems) {
				items.append(specificationItem.getName()+",");
			}
		}
        map.put("itemsName",ToolUtil.removeSuffix(items.toString(), ","));
    }
   

}
