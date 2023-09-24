package com.pet.commerce.core.module.member.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.model.MemberLikeBlog;
import com.pet.commerce.core.module.member.model.MemberReadHistoryBlog;
import com.pet.commerce.core.module.member.repository.MemberLikeBlogRepository;
import com.pet.commerce.core.module.member.repository.MemberReadHistoryBlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @since 2023/3/26
 */
@Slf4j
@Service
@Transactional
public class MemberReadHistoryBlogCoreService extends BaseCrudServiceImpl<MemberReadHistoryBlogRepository, MemberReadHistoryBlog, Long> {

    @Autowired
    private MemberReadHistoryBlogRepository memberReadHistoryBlogRepository;

    public List<MemberReadHistoryBlog> findByMember(Member member) {
        return memberReadHistoryBlogRepository.findAllByMemberAndDeletedByIsNullOrderByCreatedTimeDesc(member);
    }

    public void saveLikeBlog(Member member, Blog blog) {
        try {
            MemberReadHistoryBlog memberReadHistoryBlog = new MemberReadHistoryBlog();
            memberReadHistoryBlog.setMember(member);
            memberReadHistoryBlog.setBlog(blog);
            memberReadHistoryBlog.setUpdatedTime(new Date());
            memberReadHistoryBlog.setCreatedBy(member.getNickName());
            create(memberReadHistoryBlog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("save failed!!! /n" + e.getMessage());
        }
    }
}
