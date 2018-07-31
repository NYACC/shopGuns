package com.md.cart.factory;

import com.md.goods.dao.PriceTagMapper;
import com.md.goods.dao.ProductMapper;
import com.md.goods.dao.ShopMapper;
import com.md.goods.model.PriceTag;
import com.md.goods.model.Product;
import com.md.goods.model.Shop;
import com.stylefeng.guns.core.util.SpringContextHolder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 购物车项创建工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class CartItemFactory {
	private PriceTagMapper priceTagMapper=SpringContextHolder.getBean(PriceTagMapper.class);
	private ProductMapper productMapper=SpringContextHolder.getBean(ProductMapper.class);
	private ShopMapper shopMapper=SpringContextHolder.getBean(ShopMapper.class);
	
    public static CartItemFactory me() {
        return SpringContextHolder.getBean(CartItemFactory.class);
    }
    
    public PriceTag getPriceTag(Long priceTagId){
    	return priceTagMapper.selectById(priceTagId);
    }
    
    public Product getProduct(Long productId){
    	return productMapper.selectById(productId);
    }

	public Shop getShop(Long shopId) {
		return shopMapper.selectById(shopId);
	}
}
