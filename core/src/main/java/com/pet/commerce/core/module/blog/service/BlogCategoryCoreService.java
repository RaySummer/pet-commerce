package com.pet.commerce.core.module.blog.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.blog.model.BlogCategory;
import com.pet.commerce.core.module.blog.model.QBlogCategory;
import com.pet.commerce.core.module.blog.repository.BlogCategoryRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BlogCategoryCoreService
 *
 * @author : ray
 * @since : 1.0 2023/09/19
 **/
@Slf4j
@Service
@Transactional
public class BlogCategoryCoreService extends BaseCrudServiceImpl<BlogCategoryRepository, BlogCategory, Long> {

    public List<BlogCategory> findCategoriesByKeys(List<String> categoryKeys) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QBlogCategory.blogCategory.categoryKey.in(categoryKeys));
        return search(builder);
    }

}
