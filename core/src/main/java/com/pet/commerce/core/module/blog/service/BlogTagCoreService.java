//package com.pet.commerce.core.module.blog.service;
//
//import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
//import com.pet.commerce.core.module.blog.model.BlogCategory;
//import com.pet.commerce.core.module.blog.model.BlogTag;
//import com.pet.commerce.core.module.blog.model.QBlogCategory;
//import com.pet.commerce.core.module.blog.model.QBlogTag;
//import com.pet.commerce.core.module.blog.repository.BlogCategoryRepository;
//import com.pet.commerce.core.module.blog.repository.BlogTagRepository;
//import com.querydsl.core.BooleanBuilder;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.ObjectUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * BlogTagCoreService
// *
// * @author : ray
// * @since : 1.0 2023/09/19
// **/
//@Slf4j
//@Service
//@Transactional
//public class BlogTagCoreService extends BaseCrudServiceImpl<BlogTagRepository, BlogTag, Long> {
//
//    public List<BlogTag> findOrCreateTagsByName(List<String> tagNames) {
//        List<BlogTag> tags = new ArrayList<>();
//        for (String tagName : tagNames) {
//            BooleanBuilder builder = new BooleanBuilder();
//            builder.and(QBlogTag.blogTag.content.eq(tagName));
//            BlogTag blogTag = findOne(builder);
//            if (ObjectUtils.isEmpty(blogTag)) {
//                blogTag = new BlogTag();
//                blogTag.setContent(tagName);
//                blogTag.setActive(Boolean.TRUE);
//                blogTag.setRecommend(Boolean.FALSE);
//                save(blogTag);
//            }
//            tags.add(blogTag);
//        }
//        return tags;
//    }
//
//}
//
