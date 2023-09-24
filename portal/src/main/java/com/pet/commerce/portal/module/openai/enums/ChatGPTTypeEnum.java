package com.pet.commerce.portal.module.openai.enums;

/**
 * @author Ray
 * @since 2023/3/3
 */
public enum ChatGPTTypeEnum {

    TO_GPT("to_gpt"),

    TO_ME("to_me");

    private String name;

    ChatGPTTypeEnum(String name) {
        this.name = name;
    }
}
