package com.stylefeng.guns.core.page;

import com.stylefeng.guns.core.constant.CategoryStatus;

/**
 * 分类的状态
 *
 * @author 
 * @Date 
 */
public enum Page {

    PAGESIZE(8, "分页大小");

    int code;
    String message;

    Page(int code, String message) {
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
