package com.stylefeng.guns.common.constant.state;

/**
 * 上下架的状态
 *
 * @author 
 * @Date 
 */
public enum Marketable {

    UPPER(1, "上架"),
    LOWER(0, "下架");

    int code;
    String message;

    Marketable(int code, String message) {
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
            for (Marketable s : Marketable.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
