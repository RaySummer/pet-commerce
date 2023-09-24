//package com.pet.commerce.core.module.blog.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.pet.commerce.core.module.base.model.BaseAuditEntity;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * BlogTag
// *
// * @author : ray
// * @since : 1.0 2023/09/15
// **/
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Access(AccessType.FIELD)
//@Entity
//@Table(name = "BLOG_TAG", schema = "public", uniqueConstraints = {
//        @UniqueConstraint(columnNames = "uid")
//})
//public class BlogTag extends BaseAuditEntity {
//
//    @Column
//    private Long displayOrder;
//
//    @Column(nullable = false)
//    private Boolean active;
//
//    @Column(nullable = false)
//    private String content;
//
//    @Column(nullable = false)
//    private Boolean recommend;
//
//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newsTag", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<BlogRBlogTag> blogRBlogTagSet = new HashSet<>();
//}
