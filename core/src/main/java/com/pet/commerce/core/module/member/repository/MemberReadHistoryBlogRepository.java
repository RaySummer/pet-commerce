package com.pet.commerce.core.module.member.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.model.MemberReadHistoryBlog;

import java.util.List;

/**
 * @author Ray
 * @since 2023/3/26
 */
public interface MemberReadHistoryBlogRepository extends BaseRepository<MemberReadHistoryBlog, Long> {

    List<MemberReadHistoryBlog> findAllByMemberAndDeletedByIsNullOrderByCreatedTimeDesc(Member member);
}
