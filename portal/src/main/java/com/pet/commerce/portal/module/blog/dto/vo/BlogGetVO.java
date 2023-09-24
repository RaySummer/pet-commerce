package com.pet.commerce.portal.module.blog.dto.vo;

import com.pet.commerce.core.module.base.dto.BaseDto;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.blog.model.BlogRBlogCategory;
import com.pet.commerce.core.module.media.dto.MediaDto;
import com.pet.commerce.portal.module.member.dto.vo.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
public class BlogGetVO extends BaseDto implements Serializable {
    private static final long serialVersionUID = -739892117964094096L;

    private String title;

    private String content;

    private String description;

    private String author;

    private Date publishTime;

    private Integer likeCount;

    private Integer unlikeCount;

    private Integer readCount;

    private Integer commentCount;

    private String bannerImage;

    private String coverImage;

    private MemberVO member;

    private Set<BlogCategoryVO> blogCategoryVOSet = new HashSet<>();

//    private Set<BlogTagVO> blogTagVOSet = new HashSet<>();

    private Set<MediaDto> mediaDtoSet = new HashSet<>();

    public static BlogGetVO of(Blog blog) {
        if (ObjectUtils.isEmpty(blog)) {
            return null;
        }
        BlogGetVO vo = new BlogGetVO();
        vo.setPublishTime(blog.getPublishTime());
        vo.setAuthor(blog.getAuthor());
        vo.setLikeCount(blog.getLikeCount());
        vo.setUnlikeCount(blog.getUnlikeCount());
        vo.setReadCount(blog.getReadCount());
        vo.setCommentCount(blog.getCommentCount());
        vo.setBannerImage(blog.getBannerImage());
        vo.setCoverImage(blog.getCoverImage());
        vo.setTitle(blog.getTitle());
        vo.setContent(blog.getContent());
        vo.setDescription(blog.getDescription());

        Set<BlogRBlogCategory> blogRBlogCategories = blog.getBlogRBlogCategories();
        Set<BlogCategoryVO> blogCategoryVOS = new HashSet<>();
        blogRBlogCategories.stream().map(BlogRBlogCategory::getBlogCategory).filter(category -> ObjectUtils.isNotEmpty(category)
                && category.isActive()
                && StringUtils.isBlank(category.getDeletedBy())).forEach(category -> {
            BlogCategoryVO blogCategoryVO = new BlogCategoryVO();
            blogCategoryVO.setCategoryKey(category.getCategoryKey());
            blogCategoryVO.setName(category.getName());
            blogCategoryVO.setImage(category.getImage());
            blogCategoryVOS.add(blogCategoryVO);
        });
        vo.getBlogCategoryVOSet().addAll(blogCategoryVOS);

//        Set<BlogRBlogTag> blogRBlogTags = blog.getBlogRBlogTags();
//        Set<BlogTagVO> blogTagVOSet = new HashSet<>();
//        blogRBlogTags.stream().map(BlogRBlogTag::getBlogTag).filter(blogTag -> ObjectUtils.isNotEmpty(blogTag) && blogTag.getActive() && StringUtils.isBlank(blogTag.getDeletedBy())).forEach(blogTag -> {
//            BlogTagVO blogTagVO = new BlogTagVO();
//            blogTagVO.setContent(blogTag.getContent());
//            blogTagVOSet.add(blogTagVO);
//        });
//        vo.getBlogTagVOSet().addAll(blogTagVOSet);

        Set<MediaDto> mediaDtoSet = new HashSet<>();
        blog.getMediaSet().forEach(media -> {
            MediaDto mediaDto = new MediaDto();
            mediaDto.setType(media.getType());
            mediaDto.setOriginal(media.getOriginal());
            mediaDto.setReduce(media.getReduce());
            mediaDto.setThumbnail(media.getThumbnail());
            mediaDto.setSize(media.getSize());
            mediaDtoSet.add(mediaDto);
        });
        vo.getMediaDtoSet().addAll(mediaDtoSet);

        vo.setMember(MemberVO.of(blog.getMember()));

        return vo;
    }

    public static List<BlogGetVO> ofList(List<Blog> blogList) {
        if (blogList.isEmpty()) {
            return null;
        }
        return blogList.stream().map(BlogGetVO::of).collect(Collectors.toList());
    }
}
