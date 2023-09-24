package com.pet.commerce.portal.module.openai.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ray
 * @since 2023/3/13
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIConfigVO {

    private String apiKey;

    private String organizationId;

    private String openAIUrl;

}
