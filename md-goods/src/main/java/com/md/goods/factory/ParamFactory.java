package com.md.goods.factory;

import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.md.goods.dao.ParamItemsMapper;
import com.md.goods.model.ParamItems;
import com.stylefeng.guns.core.support.StrKit;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 参数创建工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class ParamFactory {
	private ParamItemsMapper itemsMapper=SpringContextHolder.getBean(ParamItemsMapper.class);
	public static ParamFactory me() {
        return SpringContextHolder.getBean(ParamFactory.class);
    }

	/**
	 * 获取参数项的名称(多个)
	 */
	public String getItemsNames(String itemsIds) {
		if(ToolUtil.isEmpty(itemsIds)){
			return null;
		}
		String[] items = itemsIds.split(",");
		StringBuilder sb = new StringBuilder();
		for (String item : items) {
			ParamItems itemObj = itemsMapper.selectById(Long.parseLong(item));
			if (ToolUtil.isNotEmpty(itemObj) && ToolUtil.isNotEmpty(itemObj.getName())) {
				sb.append(itemObj.getName()).append(",");
			}
		}
		return StrKit.removeSuffix(sb.toString(), ",");
	}
	/**
	 * 获取参数项的ids(多个)
	 */
	public String getItemsIds(List<ParamItems> itemsList) {
		if(ToolUtil.isEmpty(itemsList)){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (ParamItems item : itemsList) {
				sb.append(item.getId()).append(",");
		}
		return StrKit.removeSuffix(sb.toString(), ",");
	}
}
