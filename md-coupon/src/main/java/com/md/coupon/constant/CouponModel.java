package com.md.coupon.constant;

/**
 * 优惠模式
 *
 * @author 
 * @Date 
 */
public enum CouponModel {
	RECEIVECOUPON(1, "领取券"),
    GIVECOUPON(2, "赠送券");
    int code;
    String message;

    CouponModel(int code, String message) {
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
            for (CouponModel s : CouponModel.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
