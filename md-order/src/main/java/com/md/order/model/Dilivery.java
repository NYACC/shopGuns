package com.md.order.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_dilivery")
public class Dilivery {
    /**
     * 主键id
     */
    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    //配送方式
    private String diliveryWay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiliveryWay() {
        return diliveryWay;
    }

    public void setDiliveryWay(String diliveryWay) {
        this.diliveryWay = diliveryWay;
    }
}
