package com.md.cart.constant;

/**
 * 购物车项是否有效枚举
 *
 * @author fengshuonan
 * @date 2017年6月1日22:50:11
 */
public enum CartStatus {

    YES(1, "有效"),
    NO(0, "无效");

    int code;
    String message;

    CartStatus(int code, String message) {
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
            for (CartStatus s : CartStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
