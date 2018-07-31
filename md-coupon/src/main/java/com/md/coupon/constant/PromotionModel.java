package com.md.coupon.constant;

/**
 * 优惠模式
 *
 * @author 
 * @Date 
 */
public enum PromotionModel {
    REDUCE(1, "满减"),
    DISCOUNT(2, "折扣"),
	GIVE_COUPON(3,"满送优惠卷");

    int code;
    String message;

    PromotionModel(int code, String message) {
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
            for (PromotionModel s : PromotionModel.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
