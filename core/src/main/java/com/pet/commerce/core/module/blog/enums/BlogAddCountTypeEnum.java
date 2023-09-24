package com.pet.commerce.core.module.blog.enums;

import java.util.Objects;

/**
 * MediaTypeEnum
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
public enum BlogAddCountTypeEnum {

    READ("read"),

    LIKE("like"),

    UNLIKE("unlike"),

    COMMENT("comment");

    private final String value;

    BlogAddCountTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static BlogAddCountTypeEnum of(String value) {
        BlogAddCountTypeEnum[] enums = BlogAddCountTypeEnum.values();
        for (BlogAddCountTypeEnum target : enums) {
            if (Objects.equals(target.getValue(), value)) {
                return target;
            }
        }
        return null;
    }
}
