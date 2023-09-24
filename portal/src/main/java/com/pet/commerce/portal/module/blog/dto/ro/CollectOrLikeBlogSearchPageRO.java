package com.pet.commerce.portal.module.blog.dto.ro;

import com.pet.commerce.core.module.base.ro.PageRO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author Ray
 * @since 2023/7/11
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectOrLikeBlogSearchPageRO extends PageRO {
    private static final long serialVersionUID = -5229762037576361735L;

    private String keyword;
    private String author;
    private String categoryKey;

    public static CollectOrLikeBlogSearchPageRO convertDto(CollectOrLikeBlogSearchPageRO ro) {
        CollectOrLikeBlogSearchPageRO dto = new CollectOrLikeBlogSearchPageRO();
        BeanUtils.copyProperties(ro, dto);
        return dto;
    }
}
