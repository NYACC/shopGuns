package com.md.order.constant;

/**
 * 出入库类型
 *
 * @author 
 * @Date 
 */
public enum InventoryType {

    OUTPUT(0, "出库"),
    INPUT(1, "入库");

    int code;
    String message;

    InventoryType(int code, String message) {
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
            for (InventoryType s : InventoryType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
