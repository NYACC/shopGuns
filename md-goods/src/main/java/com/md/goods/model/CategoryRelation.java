package com.md.goods.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("shop_category_relation")
public class CategoryRelation {
    private Long categoryId;
    private Long goodsId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
