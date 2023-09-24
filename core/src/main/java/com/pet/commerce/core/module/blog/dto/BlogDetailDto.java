package com.pet.commerce.core.module.blog.dto;

import com.pet.commerce.core.module.base.dto.BaseDto;
import com.pet.commerce.core.module.blog.model.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * BlogGetVO
 *
 * @author : ray
 * @since : 1.0 2023/09/18
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogDetailDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = -739892117964094096L;

    private String title;

    private String content;

    private String description;

    private String author;

    private Boolean active = Boolean.FALSE;

    private Date publishTime;

    private Boolean isHot = Boolean.FALSE;

    private Boolean isTop = Boolean.FALSE;

    private Integer likeCount;

    private Integer unlikeCount;

    private Integer readCount;

    private Integer commentCount;

    private Boolean allowComment = Boolean.FALSE;

    private Boolean commentNeedReview = Boolean.FALSE;

    private String bannerImage;

    private String coverImage;

    private Set<BlogCategoryDetailDto> blogCategoryGetVOS = new HashSet<>();

//    private Set<BlogTagDetailDto> blogTagGetVOS = new HashSet<>();

    public static BlogDetailDto of(Blog blog) {
        if (ObjectUtils.isEmpty(blog)) {
            return null;
        }
        BlogDetailDto vo = new BlogDetailDto();
        vo.setActive(blog.isActive());
        vo.setPublishTime(blog.getPublishTime());
        vo.setAuthor(blog.getAuthor());
        vo.setAllowComment(blog.isAllowComment());
        vo.setCommentNeedReview(blog.isCommentNeedReview());
        vo.setIsHot(blog.isHot());
        vo.setIsTop(blog.isTop());
        vo.setLikeCount(blog.getLikeCount());
        vo.setUnlikeCount(blog.getUnlikeCount());
        vo.setReadCount(blog.getReadCount());
        vo.setCommentCount(blog.getCommentCount());
        vo.setBannerImage(blog.getBannerImage());
        vo.setCoverImage(blog.getCoverImage());
        vo.setTitle(blog.getTitle());
        vo.setContent(blog.getContent());
        vo.setDescription(blog.getDescription());

        vo.getBlogCategoryGetVOS().addAll(Objects.requireNonNull(BlogCategoryDetailDto.ofSet(blog.getBlogRBlogCategories())));

//        vo.getBlogTagGetVOS().addAll(Objects.requireNonNull(BlogTagDetailDto.ofList(blog.getBlogRBlogTags())));

        return vo;
    }

    public static List<BlogDetailDto> ofList(List<Blog> blogList) {
        if (blogList.isEmpty()) {
            return null;
        }
        return blogList.stream().map(BlogDetailDto::of).collect(Collectors.toList());
    }
}
