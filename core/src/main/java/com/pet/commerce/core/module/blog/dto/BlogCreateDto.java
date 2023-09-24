package com.pet.commerce.core.module.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * BlogCreateRO
 *
 * @author : ray
 * @since : 1.0 2023/09/19
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogCreateDto implements Serializable {
    private static final long serialVersionUID = -5624195590354949955L;

    private String title;

    private String content;

    private String description;

    private String author;

    private Boolean active = Boolean.FALSE;

    private Date publishTime = new Date();

    private Boolean isHot = Boolean.FALSE;

    private Boolean isTop = Boolean.FALSE;

    private Boolean allowComment = Boolean.FALSE;

    private Boolean commentNeedReview = Boolean.FALSE;

    private String bannerImage;

    private String coverImage;

    private List<String> categories;

//    private List<String> tags;

    private String type;

    private List<String> medias;

}
