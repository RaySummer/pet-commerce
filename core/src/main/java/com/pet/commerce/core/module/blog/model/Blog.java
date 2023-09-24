package com.pet.commerce.core.module.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import com.pet.commerce.core.module.media.model.Media;
import com.pet.commerce.core.module.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Blog
 *
 * @author : ray
 * @since : 1.0 2023/9/15
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "BLOG", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class Blog extends BaseAuditEntity {

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String description;

    @Column
    private Date publishTime;

    @Column
    private boolean active;

    @Column
    private boolean allowComment;

    @Column
    private boolean commentNeedReview;

    @Column
    private String bannerImage;

    @Column
    private String coverImage;

    @Column
    private boolean isHot;

    @Column
    private boolean isTop;

    @Column
    private int likeCount;

    @Column
    private int unlikeCount;

    @Column
    private int readCount;

    @Column
    private int commentCount;

    @Column
    private String author;

    @Column
    private String type;

    @Column
    private String adCode;

    @Column
    private double longitude;

    @Column
    private double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BlogRBlogCategory> blogRBlogCategories = new HashSet<>();

//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<BlogRBlogTag> blogRBlogTags = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BlogComment> blogComments = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Media> mediaSet = new HashSet<>();
}
