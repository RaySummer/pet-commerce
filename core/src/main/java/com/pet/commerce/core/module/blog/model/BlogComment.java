package com.pet.commerce.core.module.blog.model;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import com.pet.commerce.core.module.base.model.BaseAuditJsonTreeEntity;
import com.pet.commerce.core.module.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * BlogComment
 *
 * @author : ray
 * @since : 1.0 2023/09/15
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "BLOG_COMMENT", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class BlogComment extends BaseAuditJsonTreeEntity<BlogComment> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column
    private int level;

    @Column
    private boolean active;

    @Column
    private String image;

    @Column
    private String content;

    @Column
    private boolean isHot;

    @Column
    private boolean isTop;

    @Column
    private int likeCount;

    @Column
    private int unlikeCount;

    @Column
    private int replyCount;

}
