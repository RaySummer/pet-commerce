package com.pet.commerce.core.module.member.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.model.MemberCollectBlog;
import com.pet.commerce.core.module.member.model.MemberLikeBlog;

import java.util.List;

/**
 * @author Ray
 * @since 2023/3/26
 */
public interface MemberLikeBlogRepository extends BaseRepository<MemberLikeBlog, Long> {

    List<MemberLikeBlog> findAllByMemberAndDeletedByIsNullOrderByCreatedTimeDesc(Member member);
}
