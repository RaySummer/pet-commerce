package com.pet.commerce.core.exception;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


/**
 * Author: Happen
 * Date: 2022/6/16 16:26
 **/
public class NotFoundException extends RuntimeException {

    public static final String FORMAT = "error.className.not.found";
    private final String className;
    private final Object[] args;

    public NotFoundException(Class<?> clazz, Object... args) {
        this.className = clazz.getSimpleName().toLowerCase(Locale.ROOT);
        this.args = args;
    }

    public String getCode() {
        return FORMAT.replace("className", className);
    }

    public String getDescription(String message) {
        List<Object> params = Lists.newArrayList(className);
        if (args != null && args.length > 0) {
            params.addAll(Arrays.asList(args));
        }
        return String.format(message, params.toArray(new Object[0]));
    }
}
