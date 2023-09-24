package com.pet.commerce.core.module.media.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * MedaiDto
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto implements Serializable {
    private static final long serialVersionUID = -3961899894414515789L;

    private String type;

    private Long size;

    private String original;

    private String reduce;

    private String thumbnail;

}
