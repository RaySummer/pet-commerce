package com.pet.commerce.portal.module.openai.dto.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/3/7
 */
@Getter
@Setter
@Builder
public class OpenAISpeechToTextVO implements Serializable {
    private static final long serialVersionUID = -5019174033219886876L;

    private String key;

    private String content;

}
