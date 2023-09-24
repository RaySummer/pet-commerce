//package com.pet.commerce.core.module.blog.model;
//
//import com.pet.commerce.core.module.base.model.BaseAuditEntity;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//
///**
// * BlogRBlogTag
// *
// * @author : ray
// * @since : 1.0 2023/09/18
// **/
//@NoArgsConstructor
//@AllArgsConstructor
//@Setter
//@Getter
//@Entity
//@Table(name = "BLOG_R_BLOG_TAG", schema = "public", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"blog_id", "tag_id"}),
//        @UniqueConstraint(columnNames = "uid")})
//public class BlogRBlogTag extends BaseAuditEntity {
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "blog_id", nullable = false)
//    private Blog blog;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "tag_id", nullable = false)
//    private BlogTag blogTag;
//}
