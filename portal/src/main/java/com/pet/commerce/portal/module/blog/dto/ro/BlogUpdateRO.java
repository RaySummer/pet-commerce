package com.pet.commerce.portal.module.blog.dto.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * BlogUpdateRO
 *
 * @author : ray
 * @since : 1.0 2023/09/19
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogUpdateRO implements Serializable {

    private static final long serialVersionUID = -7699646062515016725L;

    private String content;

    private String bannerImage;

    private String coverImage;

    private List<String> categories;

//    private List<String> tags;

    private List<String> mediaLinks;

}
