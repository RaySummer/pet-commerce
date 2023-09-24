package com.pet.commerce.core.module.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Molly
 * @since 2022-02-23
 */
@Getter
@Setter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> implements Serializable {

    public static final int SUCCESS = 0;
    public static final int FAILED = 1;

    private int status = SUCCESS;

    private T data;

    private List<ErrorDTO> errors = new ArrayList<>();

    /**
     * 构造成功响应对象
     *
     * @param <T> 响应数据
     */
    public static <T> Result<T> ok(T data) {
        return builder(SUCCESS, data);
    }

    /**
     * 构造成功响应对象
     */
    public static <T> Result<T> ok() {
        return builder(SUCCESS, null);
    }

    /**
     * 构造错误响应对象
     */
    public static <T> Result<T> fail() {
        return builder(FAILED, null);
    }

    public static <T> Result<T> fail(String... messages) {
        List<ErrorDTO> errors = Lists.newArrayList();
        if (messages != null && messages.length > 0) {
            for (String message : messages) {
                ErrorDTO error = ErrorDTO.builder()
                        .errorCode(message)
                        .description(message)
                        .field(null)
                        .build();
                errors.add(error);
            }
        }
        return builder(FAILED, null, errors.toArray(new ErrorDTO[0]));
    }

    /**
     * 构造错误响应对象
     */
    public static <T> Result<T> fail(String errorMsg) {
        ErrorDTO error = ErrorDTO.builder()
                .errorCode("500")
                .description(errorMsg)
                .field(null)
                .build();
        return builder(FAILED, null, error);
    }

    public Result<T> addError(String errorCode, String description, String field) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        ErrorDTO error = ErrorDTO.builder().errorCode(errorCode).description(description).field(field).build();
        errors.add(error);
        return this;
    }

    private static <T> Result<T> builder(int status, T data, ErrorDTO... errors) {
        Result<T> result = new Result<>();
        result.setStatus(status);
        result.setData(data);
        if (errors != null && errors.length > 0) {
            result.setErrors(Arrays.asList(errors));
        }
        return result;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return status == SUCCESS;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorDTO implements Serializable {
        private static final long serialVersionUID = 415973670200892978L;

        private String errorCode;

        private String field;

        private String description;

    }
}
