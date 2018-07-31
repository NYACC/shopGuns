package com.stylefeng.guns.core.constant;

/**
 * 分类的状态
 *
 * @author 
 * @Date 
 */
public enum CategoryStatus {

    ENABLE(1, "启用"),
    DISABLE(0, "停用");

    int code;
    String message;

    CategoryStatus(int code, String message) {
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
            for (CategoryStatus s : CategoryStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
