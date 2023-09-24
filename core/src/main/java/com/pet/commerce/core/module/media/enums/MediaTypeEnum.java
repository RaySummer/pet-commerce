package com.pet.commerce.core.module.media.enums;

import java.util.Objects;

/**
 * MediaTypeEnum
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
public enum MediaTypeEnum {

    IMAGE("image"),

    VIDEO("video"),

    ZIP("zip"),

    DOC("doc");

    private final String value;

    MediaTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static MediaTypeEnum of(String value) {
        MediaTypeEnum[] enums = MediaTypeEnum.values();
        for (MediaTypeEnum target : enums) {
            if (Objects.equals(target.getValue(), value)) {
                return target;
            }
        }
        return null;
    }
}
