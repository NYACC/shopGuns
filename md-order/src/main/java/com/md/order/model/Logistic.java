package com.md.order.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

@TableName("shop_logistic")
public class Logistic {
    /**
     * 主键id
     */
    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    //物流号
    private String logisticsNum;
    //物流公司
    private String logisticsFirm;
    //区域运费
    private BigDecimal areaFee;
    //首重费用
    private BigDecimal firstFee;
    //每斤费用
    private BigDecimal unitFee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum;
    }

    public String getLogisticsFirm() {
        return logisticsFirm;
    }

    public void setLogisticsFirm(String logisticsFirm) {
        this.logisticsFirm = logisticsFirm;
    }

    public BigDecimal getAreaFee() {
        return areaFee;
    }

    public void setAreaFee(BigDecimal areaFee) {
        this.areaFee = areaFee;
    }

    public BigDecimal getFirstFee() {
        return firstFee;
    }

    public void setFirstFee(BigDecimal firstFee) {
        this.firstFee = firstFee;
    }

    public BigDecimal getUnitFee() {
        return unitFee;
    }

    public void setUnitFee(BigDecimal unitFee) {
        this.unitFee = unitFee;
    }
}
