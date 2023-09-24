package com.pet.commerce.core.module.blog.dto;

import com.pet.commerce.core.module.blog.model.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class BlogUpdateDto implements Serializable {

    private static final long serialVersionUID = -7699646062515016725L;

    private String title;

    private String content;

    private String description;

    private Boolean active;

    private Date publishTime;

    private Boolean isHot;

    private Boolean isTop;

    private Boolean allowComment;

    private Boolean commentNeedReview;

    private String bannerImage;

    private String coverImage;

    private List<String> categories = new ArrayList<>();

//    private List<String> tags = new ArrayList<>();

    private List<String> mediaLinks = new ArrayList<>();

    public static Blog convertRoToEntity(BlogUpdateDto ro, Blog blog) {
        if (StringUtils.isNotBlank(ro.getTitle())) {
            blog.setTitle(ro.getTitle());
        }
        if (StringUtils.isNotBlank(ro.getContent())) {
            blog.setContent(ro.getContent());
        }
        if (StringUtils.isNotBlank(ro.getDescription())) {
            blog.setDescription(ro.getDescription());
        }
        if (StringUtils.isNotBlank(ro.getBannerImage())) {
            blog.setBannerImage(ro.getBannerImage());
        }
        if (StringUtils.isNotBlank(ro.getCoverImage())) {
            blog.setCoverImage(ro.getCoverImage());
        }
        if (ro.getActive() != null) {
            blog.setActive(ro.getActive());
        }
        if (ro.getIsHot() != null) {
            blog.setHot(ro.getIsHot());
        }
        if (ro.getIsTop() != null) {
            blog.setTop(ro.getIsTop());
        }
        if (ro.getAllowComment() != null) {
            blog.setAllowComment(ro.getAllowComment());
        }
        if (ro.getCommentNeedReview() != null) {
            blog.setCommentNeedReview(ro.getCommentNeedReview());
        }
        return blog;
    }
}
