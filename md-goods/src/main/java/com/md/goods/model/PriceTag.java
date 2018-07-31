package com.md.goods.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.md.goods.constant.Marketable;

import java.math.BigDecimal;

@TableName("shop_price_tag")
public class PriceTag {
	
	@TableId(value="id", type= IdType.AUTO)
    private Long id;
    //商品编号
    private Long goodsId;
    //产品编号
    private Long productId;
    //市场价格 10
    private BigDecimal marketPrice;
    //销售价格 9
    private BigDecimal price;
    //商品积分
    private Integer integral;
    //库存预警值
    private Integer threshold;
    //商品状态1:上架   0:下架
    private Integer marketable;
    //门店编号
    private Long shopId;
    //商品的库存
    private Integer inventory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getMarketable() {
        return marketable;
    }

    public void setMarketable(Integer marketable) {
        this.marketable = marketable;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public void setProduct(Product product){
        this.productId=product.getId();
        this.marketPrice=product.getMarketPrice();
        this.price=product.getPrice();
        this.goodsId=product.getGoodsId();
        this.marketable= Marketable.LOWER.getCode();

    }
	
	public Boolean isValid(){
		if(this.inventory>0&&this.marketable==Marketable.UPPER.getCode()){
			return true;
		}
		return false;
	}

}
