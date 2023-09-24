package com.pet.commerce.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Ray
 * Date: 2023/02/28
 * 禁止重复提交，portal端需要携带浏览器指纹才生效，console端需要用户登录才生效
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {

    /**
     * 指定时间内不可重复提交，单位毫秒；默认 1000 毫秒
     */
    long timeLimit() default 1000L;
}
