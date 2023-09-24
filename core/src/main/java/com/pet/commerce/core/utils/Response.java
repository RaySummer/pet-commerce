package com.pet.commerce.core.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Rest API 通用返回JSON数据结构
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response implements Serializable {
    private static final long serialVersionUID = -9183377898797924239L;

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_SERVER_ERROR = 500;
    public static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_NOT_FOUND_ERROR = 404;
    public static final int CODE_FORBIDDEN = 403;
    public static final int CODE_ARGUMENT_ERROR = 500;

    private int code = CODE_SUCCESS;
    /**
     * 真正的数据载体
     */
    private Object data;

    /**
     * 出错信息集合
     */
    private List<ErrorItemDto> errors;

    /**
     * 当前请求的参数对象
     */
    private Object payload;

    public static Response of() {
        return new Response();
    }

    public static Response of(Object data) {
        return new Response().setData(data);
    }

    public static Response ofError() {
        return new Response().setCode(CODE_ARGUMENT_ERROR);
    }

    public static Response ofError(String errorKey) {
        return new Response().setCode(CODE_ARGUMENT_ERROR).addError(errorKey);
    }

    public static Response ofError(int code, String errorKey) {
        return new Response().setCode(code).addError(errorKey);
    }

    public static Response ofError(String errorKey, String field, String description) {
        return new Response().setCode(CODE_ARGUMENT_ERROR).addError(errorKey, field, description);
    }

    public static Response of(BindingResult bindingResult) {
        Response commonResponse = new Response();
        if (bindingResult != null && bindingResult.hasErrors()) {
            commonResponse.setCode(CODE_SERVER_ERROR);
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                if (StringUtils.startsWith(defaultMessage, "error.")) {
                    commonResponse.addError(defaultMessage);
                } else {
                    commonResponse.addError(String.format("error.%s.%s", error.getField(), error.getCode()), error.getField(), "");
                }
            }
        }

        return commonResponse;
    }

    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }

    public List<ErrorItemDto> getErrors() {
        return errors;
    }

    public Response addError(ErrorItemDto errorItem) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        errors.add(errorItem);
        return this;
    }

    public Response addError(String errorKey) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        errors.add(ErrorItemDto.builder().reference(errorKey).build());
        return this;
    }

    public Response addError(String errorKey, String field) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        errors.add(ErrorItemDto.builder().reference(errorKey).field(field).build());
        return this;
    }

    public Response addError(String errorKey, String field, String description) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(ErrorItemDto.builder().reference(errorKey).field(field).description(description).build());
        return this;
    }

    public Object getPayload() {
        return payload;
    }

    public Response setPayload(Object payload) {
        this.payload = payload;
        return this;
    }
}
