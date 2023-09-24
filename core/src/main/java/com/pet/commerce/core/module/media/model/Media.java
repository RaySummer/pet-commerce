package com.pet.commerce.core.module.media.model;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.user.model.SysUser;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Media
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "MEDIA", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class Media extends BaseAuditEntity {

    /**
     * 文件类型
     */
    @Column
    private String type;

    /**
     * 文件大小
     */
    @Column
    private long size;

    /**
     * 原始图片地址
     */
    @Column
    private String original;

    /**
     * 压缩后图片地址
     */
    @Column
    private String reduce;

    /**
     * 缩略图
     */
    @Column
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private SysUser sysUser;
}
