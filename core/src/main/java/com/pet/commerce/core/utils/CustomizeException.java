package com.pet.commerce.core.utils;

/**
 * @author Ray
 * @since 2022/3/8
 */
public class CustomizeException extends RuntimeException {
    private static final long serialVersionUID = 2483656625114243136L;

    private String msg;
    private int code = 500;

    public CustomizeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CustomizeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public CustomizeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CustomizeException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
