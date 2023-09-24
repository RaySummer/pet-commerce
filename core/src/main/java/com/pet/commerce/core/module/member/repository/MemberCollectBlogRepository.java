package com.pet.commerce.core.module.member.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.model.MemberCollectBlog;

import java.util.List;

/**
 * @author Ray
 * @since 2023/3/26
 */
public interface MemberCollectBlogRepository extends BaseRepository<MemberCollectBlog, Long> {

    void deleteByMemberAndAndBlog(Member member, Blog blog);

    List<MemberCollectBlog> findAllByMemberAndDeletedByIsNullOrderByCreatedTimeDesc(Member member);
}
