package com.md.order.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Set;

@TableName("shop_shipping")
public class Shipping {
    /**
     * 主键id
     */
    @TableId(value="id", type= IdType.AUTO)
    //发货单号
    private Long id;
    //订单编号
    private Long orderId;
    // 物流公司
    private String logisticsFirm;
    // 物流编号
    private String logisticsNum;
    // 操作员
    private String userName;
    private String sn;
    // 发货明细
    @TableField(exist=false)
    private Set<ShippingItem> shippingItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getLogisticsFirm() {
        return logisticsFirm;
    }

    public void setLogisticsFirm(String logisticsFirm) {
        this.logisticsFirm = logisticsFirm;
    }

    public String getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<ShippingItem> getShippingItems() {
        return shippingItems;
    }

    public void setShippingItems(Set<ShippingItem> shippingItems) {
        this.shippingItems = shippingItems;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
