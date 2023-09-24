package com.pet.commerce.core.module.blog.dto;

import com.pet.commerce.core.module.base.ro.PageRO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BlogSearchPageParamDto
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogSearchPageParamDto extends PageRO {
    private static final long serialVersionUID = 3416632468956885641L;

    private String keyword;
}
