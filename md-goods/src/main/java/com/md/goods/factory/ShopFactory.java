package com.md.goods.factory;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.md.goods.dao.ShopMapper;
import com.md.goods.model.Shop;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 门店创建工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class ShopFactory {
	private ShopMapper shopMapper = SpringContextHolder.getBean(ShopMapper.class);

	public static ShopFactory me() {
		return SpringContextHolder.getBean(ShopFactory.class);
	}

	/**
	 * 获取品牌状态
	 * 
	 * @param shopId
	 * @return
	 */
	public String getShopName(Long shopId) {
		Shop shop = shopMapper.selectById(shopId);
		return shop.getName();
	}

}
