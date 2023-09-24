package com.pet.commerce.core.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Awesome Pojo Generator
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorItemDto implements Serializable {
    private static final long serialVersionUID = 415973670200892978L;

    private String reference;
    private String field;
    private String description;
}
