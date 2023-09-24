package com.pet.commerce.portal.module.openai.dto.ro;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/3/1
 */
@Getter
@Setter
public class ChatGPTRO implements Serializable {
    private static final long serialVersionUID = 5509782916217177728L;

    private String content;

}
