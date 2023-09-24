package com.pet.commerce.core.module.media.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * MediaUpdateDto
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaUpdateDto implements Serializable {
    private static final long serialVersionUID = 5825169215182834584L;

    private String blogUid;

    private String productUid;

    private String memberUid;

    private String userUid;

}
