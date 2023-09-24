package com.pet.commerce.core.module.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pet.commerce.core.module.base.model.BaseAuditJsonTreeEntity;
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
 * BlogCategory
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
@Table(name = "BLOG_CATEGORY", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class BlogCategory extends BaseAuditJsonTreeEntity<BlogCategory> {

    @Column(length = 100, nullable = false)
    private String categoryKey;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1000)
    private String image;

    @Column
    private long displayOrder;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean isHot;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blogCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BlogRBlogCategory> blogRBlogCategories = new HashSet<>();

    @JsonIgnore
    public Boolean hasParent() {
        if (parent != null && !isRoot()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @JsonIgnore
    public Boolean isRoot() {
        return getParent() == null;
    }

    @JsonIgnore
    public Boolean hasChild() {
        return !getChildren().isEmpty() && getChildren().size() > 0;
    }
}
