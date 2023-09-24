package com.pet.commerce.portal.module.openai.dto.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/3/2
 */
@Getter
@Setter
@Builder
public class OpenAIGenerateImageVO implements Serializable {
    private static final long serialVersionUID = 4376941733348820978L;

    private String imageLink;
}
