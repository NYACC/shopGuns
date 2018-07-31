package com.md.order.constant;

/**
 * 订单的状态
 *
 * @author 
 * @Date 
 */
public enum EvaluationLevel {
    GOOD(0, "好评"),
    AVERAGE(1, "一般"),
    BAD(2, "差评");

    int code;
    String message;

    EvaluationLevel(int code, String message) {
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
            for (EvaluationLevel s : EvaluationLevel.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
