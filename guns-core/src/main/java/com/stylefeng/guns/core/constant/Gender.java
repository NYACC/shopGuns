package com.stylefeng.guns.core.constant;

/**
 * 性别
 *
 * @author wangxi
 * @Date 2017年1月22日 下午12:14:59
 */
public enum Gender {

    MALE(1, "男"),
    FEMALE(2, "女");

    int code;
    String message;

    Gender(int code, String message) {
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
            for (Gender s : Gender.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
