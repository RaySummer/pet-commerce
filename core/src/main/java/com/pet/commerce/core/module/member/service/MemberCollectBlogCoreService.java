package com.pet.commerce.core.module.member.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.model.MemberCollectBlog;
import com.pet.commerce.core.module.member.repository.MemberCollectBlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ray
 * @since 2023/3/26
 */
@Slf4j
@Service
@Transactional
public class MemberCollectBlogCoreService extends BaseCrudServiceImpl<MemberCollectBlogRepository, MemberCollectBlog, Long> {

    @Autowired
    private MemberCollectBlogRepository memberCollectBlogRepository;

    public void deleteCollect(Member member, Blog blog) {
        memberCollectBlogRepository.deleteByMemberAndAndBlog(member, blog);
    }

    public List<MemberCollectBlog> findByMember(Member member) {
        return memberCollectBlogRepository.findAllByMemberAndDeletedByIsNullOrderByCreatedTimeDesc(member);
    }
}
