package com.pet.commerce.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ray
 * @since 2021/9/16
 **/
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private final String businessErrorCode;
    private String[] args;


    public BusinessException(String code) {
        this.businessErrorCode = code;
    }


    public BusinessException(String code, String... args) {
        this.businessErrorCode = code;
        this.args = args;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        // 重写，禁止抓取堆栈信息
        return this;
    }

}
