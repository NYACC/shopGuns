package com.md.coupon.constant;

/**
 * 优惠模式
 *
 * @author 
 * @Date 
 */
public enum CodeStatus {
    CREATED(0, "已创建"),
    RECEIVED(1, "已领取"),
    USED(2,"已使用");

    int code;
    String message;

    CodeStatus(int code, String message) {
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
            for (CodeStatus s : CodeStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
