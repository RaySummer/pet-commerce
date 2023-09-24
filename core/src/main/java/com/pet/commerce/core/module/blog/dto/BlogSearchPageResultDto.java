package com.pet.commerce.core.module.blog.dto;

import com.pet.commerce.core.module.base.vo.BaseAuditVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * BlogSearchPageResultDto
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogSearchPageResultDto extends BaseAuditVO {
    private static final long serialVersionUID = 7536529037207964074L;

    private String title;

    private String coverImage;

    private String author;

    private Boolean active = Boolean.FALSE;

    private Date publishTime;

    private Boolean isHot;

    private Boolean isTop;

    private Integer likeCount;

    private Integer unlikeCount;

    private Integer readCount;

    private Integer commentCount;
}
