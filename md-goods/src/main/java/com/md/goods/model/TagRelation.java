package com.md.goods.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("shop_tag_relation")
public class TagRelation {
    private Long tagId;
    private Long goodsId;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
