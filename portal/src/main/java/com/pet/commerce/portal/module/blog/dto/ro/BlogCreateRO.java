package com.pet.commerce.portal.module.blog.dto.ro;

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
public class BlogCreateRO implements Serializable {
    private static final long serialVersionUID = -5624195590354949955L;

    private String title;

    private String content;

    private String description;

    private Boolean active = Boolean.TRUE;

    private Date publishTime = new Date();

    private Boolean allowComment = Boolean.TRUE;

    private String bannerImage;

    private String coverImage;

    private String type;

    private List<String> categories;

//    private List<String> tags;

    private List<String> medias;

}
