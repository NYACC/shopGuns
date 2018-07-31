package com.md.coupon.constant;

/**
 * 促销的状态
 *
 * @author 
 * @Date 
 */
public enum PromotionStatus {
    NOT_START(0,"未开始"),
	RUNNING(1, "进行中"),
    FINISH(2, "已结束"),
    DISABLE(3,"停用");

    int code;
    String message;

    PromotionStatus(int code, String message) {
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
            for (PromotionStatus s : PromotionStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
