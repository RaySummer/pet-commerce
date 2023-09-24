package com.pet.commerce.core.utils;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringValueResolver;

@Repository
@Getter
public class SpringUtil implements ApplicationContextAware, EmbeddedValueResolverAware {

    private static ApplicationContext ctx = null;

    private static StringValueResolver resolver = null;

    public static SpringUtil getInstance() {
        return SpringUtil.getBean(SpringUtil.class);
    }

    /**
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ctx == null) {
            ctx = applicationContext;
        }
    }

    /**
     * 推荐使用getBean(clazz)方法，不需要类型转换
     */
    static public Object getBean(String name) {
        return ctx.getBean(name);
    }

    /**
     * 使用泛型获取bean， 该bean对象必须在spring中注册
     *
     * @param <T>   泛型类型定义
     * @param clazz 需要获取bean的类
     * @author huanghaiping
     */
    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    /**
     * 重写setEmbeddedValueResolver方法，将StringValueResolver对象保存到当前工具类的静态成员变量resolver中,
     * 从StringValueResolver对象中可获取properties文件中的属性名称和值
     *
     * @param stringValueResolver
     * @author Molly
     * @date 2018-9-29
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        if (resolver == null) {
            resolver = stringValueResolver;
        }
    }

}
