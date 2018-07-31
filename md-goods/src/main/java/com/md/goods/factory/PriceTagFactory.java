package com.md.goods.factory;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.md.goods.constant.Marketable;
import com.md.goods.dao.GoodsMapper;
import com.md.goods.dao.PriceTagMapper;
import com.md.goods.dao.ProductMapper;
import com.md.goods.dao.ShopMapper;
import com.md.goods.model.PriceTag;
import com.md.goods.model.Product;
import com.md.goods.model.Shop;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 分类创建工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class PriceTagFactory {
	private ProductMapper productMapper = SpringContextHolder.getBean(ProductMapper.class);
	private ShopMapper storesMapper=SpringContextHolder.getBean(ShopMapper.class);
	private PriceTagMapper priceTagMapper=SpringContextHolder.getBean(PriceTagMapper.class);
	private GoodsMapper goodsMapper=SpringContextHolder.getBean(GoodsMapper.class);
	
	public static PriceTagFactory me() {
        return SpringContextHolder.getBean(PriceTagFactory.class);
    }
	
	/**
     * 获取价格标签的预期价格
     * @param id
     * @return
     */
	public BigDecimal getExpectedPrice(Long id) {
		Product selectById = productMapper.selectById(id);
		return selectById.getMarketPrice();
	}
	/**
	 * 由规格商品创建价格标签
	 * @param product
	 */
	public void createPriceTag(Product product){
		Wrapper<Shop> wrapper=new EntityWrapper<>();
		List<Shop> shops = storesMapper.selectList(wrapper);
		for (Shop shop : shops) {
			PriceTag priceTag = new PriceTag();
			priceTag.setProduct(product);
			priceTag.setShopId(shop.getId());
			priceTag.setInventory(0);
			priceTagMapper.insert(priceTag);
		}
	}
	/**
	 * 由门店创建价格标签
	 * @param shopId
	 */
	public void createPriceTag(Long shopId){
		Wrapper<Product> wrapper=new EntityWrapper<>();
		List<Product> products = productMapper.selectList(wrapper);
		for (Product product : products) {
			PriceTag priceTag = new PriceTag();
			priceTag.setProduct(product);
			priceTag.setShopId(shopId);
			priceTag.setInventory(0);
			priceTagMapper.insert(priceTag);
		}
	}

	/**
	 * 根据门店编号查找门店对应的名称
	 * @param id
	 * @return
	 */
	public String getStoresName(Long id){
		Shop shop = storesMapper.selectById(id);
		if (ToolUtil.isNotEmpty(shop)) {
			return shop.getName();
		}else{
			return null;
		}
	}
	public String getMarketable(Integer marketable){
		return Marketable.valueOf(marketable);
	}
	/**
	 * 获取商品名称
	 * @param id
	 * @return
	 */
	public String getGoodsName(Long id){
		return goodsMapper.selectById(id).getName();
	}
	/**
	 * 获取规格名称
	 * @param id
	 * @return
	 */
	public String getProductName(Long id){
		return productMapper.selectById(id).getName();
	}
	
	/**
	 * 获取规格图片
	 * @param id
	 * @return
	 */
	public Long getProductImage(Long id){
		return productMapper.selectById(id).getImage();
	}
}
