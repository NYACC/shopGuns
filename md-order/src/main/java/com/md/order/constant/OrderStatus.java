package com.md.order.constant;

/**
 * 订单的状态
 *
 * @author 
 * @Date 
 */
public enum OrderStatus {
    //待付款、待审核、待发货、待收货、完成、关闭
    OBLIGATION(0, "待付款"),
    UNCHECK(1, "待审核"),
    WAIT_SEND(2, "待发货"),		//已付款
    WAIT_GAINS(3,"待收货"),		//已发货
    WAIT_EVALUATE(6,"待评价"),   //确认收货
    TRADE_SUCCESS(4, "交易完成"),
    DELETE(-1, "删除订单"),	//删除
    WAIT_REFUND(7, "待退款"),
    REFUND(8, "已退款"),
    TRADE_CLOSE(5, "交易关闭");  //取消订单

    int code;
    String message;

    OrderStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (OrderStatus s : OrderStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
