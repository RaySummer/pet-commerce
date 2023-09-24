package com.pet.commerce.core.module.blog.dto;

import com.pet.commerce.core.module.blog.model.BlogCategory;
import com.pet.commerce.core.module.blog.model.BlogRBlogCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * BlogCategoryGetVO
 *
 * @author : ray
 * @since : 1.0 2023/09/18
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogCategoryDetailDto implements Serializable {
    private static final long serialVersionUID = -8112395887449552242L;

    private String uuid;

    private String categoryKey;

    private String name;

    private Long displayOrder;

    private Boolean isHot = Boolean.FALSE;

    private Boolean active = Boolean.FALSE;

    private String image;

    private BlogCategoryDetailDto parent;

    private Set<BlogCategoryDetailDto> child = new HashSet<>();

    public static BlogCategoryDetailDto of(BlogCategory blogCategory) {
        if (ObjectUtils.isEmpty(blogCategory)) {
            return null;
        }
        BlogCategoryDetailDto vo = new BlogCategoryDetailDto();
        vo.setUuid(blogCategory.getUidStr());
        vo.setActive(blogCategory.isActive());
        vo.setCategoryKey(blogCategory.getCategoryKey());
        vo.setName(blogCategory.getName());
        vo.setDisplayOrder(blogCategory.getDisplayOrder());
        vo.setImage(blogCategory.getImage());
//        vo.getLocales().addAll(Objects.requireNonNull(NewsCategoryLocaleGetVO.ofSet(BlogCategory.getLocaleSet())));

        if (blogCategory.hasParent()) {
            vo.setParent(BlogCategoryDetailDto.of(blogCategory.getParent()));
        }
        if (blogCategory.hasChild()) {
            vo.getChild().clear();
            vo.getChild().addAll(Objects.requireNonNull(ofSetForChild(blogCategory.getChildren())));
        }

        return vo;
    }

    public static Set<BlogCategoryDetailDto> ofSet(Set<BlogRBlogCategory> blogRBlogCategorySet) {
        if (blogRBlogCategorySet.isEmpty()) {
            return null;
        }
        return blogRBlogCategorySet.stream().map(blogCategory -> of(blogCategory.getBlogCategory())).collect(Collectors.toSet());
    }

    public static Set<BlogCategoryDetailDto> ofSetForChild(Set<BlogCategory> newsCategoryList) {
        if (newsCategoryList.isEmpty()) {
            return null;
        }
        return newsCategoryList.stream().map(BlogCategoryDetailDto::of).collect(Collectors.toSet());
    }

}
