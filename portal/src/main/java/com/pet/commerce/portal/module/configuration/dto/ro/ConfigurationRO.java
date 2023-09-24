package com.pet.commerce.portal.module.configuration.dto.ro;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/3/13
 */
@Getter
@Setter
public class ConfigurationRO implements Serializable {
    private static final long serialVersionUID = 455004470284640444L;

    private String platform;

    private String key;

    private String value;

    private String description;
}
