package com.pet.commerce.core.aspect;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequest {

    //毫秒，分钟，小时 之间的转换用算数
    long time() default 60000 * 60 * 24; // 限制时间 单位：毫秒

    int count() default Integer.MAX_VALUE; // 允许请求的次数

}
