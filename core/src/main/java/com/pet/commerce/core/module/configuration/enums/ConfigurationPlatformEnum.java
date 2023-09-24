package com.pet.commerce.core.module.configuration.enums;

/**
 * @author Ray
 * @since 2023/3/3
 */
public enum ConfigurationPlatformEnum {

    BACKSTAGE("backstage"),

    PORTAL("portal");

    private String name;

    ConfigurationPlatformEnum(String name) {
        this.name = name;
    }
}
