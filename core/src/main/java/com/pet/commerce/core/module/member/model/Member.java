package com.pet.commerce.core.module.member.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import com.pet.commerce.core.module.media.model.Media;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ray
 * @since 2023/2/14
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "member", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class Member extends BaseAuditEntity {

    @Column
    private String nickName;

    @Column
    private boolean gender;

    @Column
    private String mobileNumber;

    @Column
    private String email;

    @Column
    private String account;

    @Column
    private String avatar;

    @Column
    private String password;

    @Column
    private String backImage;

    @Column
    private String birthday;

    @Column
    private String address;

    @Column
    private String adCode;

    @Column
    private double longitude;

    @Column
    private double latitude;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Media> mediaSet = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberCollectBlog> collectBlogs;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberLikeBlog> likeBlogs;

}
