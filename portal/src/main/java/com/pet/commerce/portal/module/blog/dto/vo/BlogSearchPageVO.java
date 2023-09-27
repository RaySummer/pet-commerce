package com.pet.commerce.portal.module.blog.dto.vo;

import com.pet.commerce.core.module.base.vo.BaseAuditVO;
import com.pet.commerce.core.module.blog.dto.BlogSearchPageResultDto;
import com.pet.commerce.core.module.blog.model.BlogCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * NewsSearchPageVO
 *
 * @author : ray
 * @since : 1.0 2023/09/18
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogSearchPageVO extends BaseAuditVO {

    private String title;

    private String coverImage;

    private String author;

    private Date publishTime;

    private Boolean isHot;

    private Boolean isTop;

    private Integer likeCount = 0;

    private Integer unlikeCount = 0;

    private Integer readCount = 0;

    private Integer commentCount = 0;

    private List<BlogCategory> categories = new ArrayList<>();

//    private List<BlogTag> blogTagList;

    public static BlogSearchPageVO convertVO(BlogSearchPageResultDto dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }
        BlogSearchPageVO vo = new BlogSearchPageVO();
        vo.setTitle(dto.getTitle());
        vo.setCoverImage(dto.getCoverImage());
        vo.setAuthor(dto.getAuthor());
        vo.setPublishTime(dto.getPublishTime());
        vo.setIsHot(dto.getIsHot());
        vo.setIsTop(dto.getIsTop());
        vo.setLikeCount(dto.getLikeCount());
        vo.setUnlikeCount(dto.getUnlikeCount());
        vo.setReadCount(dto.getReadCount());
        vo.setCommentCount(dto.getCommentCount());
        return vo;
    }

    public static List<BlogSearchPageVO> convertVOList(List<BlogSearchPageResultDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().map(BlogSearchPageVO::convertVO).collect(Collectors.toList());
    }
}
