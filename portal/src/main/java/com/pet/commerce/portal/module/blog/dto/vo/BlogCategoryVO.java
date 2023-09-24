package com.pet.commerce.portal.module.blog.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * BlogCategoryVO
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogCategoryVO implements Serializable {
    private static final long serialVersionUID = -8757696687021817982L;

    private String categoryKey;

    private String name;

    private String image;
}
