package com.pet.commerce.portal.module.blog.dto.ro;

import com.pet.commerce.core.module.base.ro.PageRO;
import com.pet.commerce.core.module.blog.dto.BlogSearchPageParamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author Ray
 * @since 2023/7/11
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogSearchPageRO extends PageRO {
    private static final long serialVersionUID = -5229762037576361735L;

    private String keyword;
    private String author;
    private String categoryKey;
//    private List<String> tagList;
    private Boolean isHot = Boolean.FALSE;
    private Boolean isTop = Boolean.FALSE;

    public static BlogSearchPageParamDto convertDto(BlogSearchPageRO ro) {
        BlogSearchPageParamDto dto = new BlogSearchPageParamDto();
        BeanUtils.copyProperties(ro, dto);
        return dto;
    }
}
