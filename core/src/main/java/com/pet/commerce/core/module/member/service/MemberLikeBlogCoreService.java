package com.pet.commerce.core.module.member.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.model.MemberCollectBlog;
import com.pet.commerce.core.module.member.model.MemberLikeBlog;
import com.pet.commerce.core.module.member.repository.MemberCollectBlogRepository;
import com.pet.commerce.core.module.member.repository.MemberLikeBlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
public class MemberLikeBlogCoreService extends BaseCrudServiceImpl<MemberLikeBlogRepository, MemberLikeBlog, Long> {

    @Autowired
    private MemberLikeBlogRepository memberLikeBlogRepository;

    public List<MemberLikeBlog> findByMember(Member member) {
        return memberLikeBlogRepository.findAllByMemberAndDeletedByIsNullOrderByCreatedTimeDesc(member);
    }

    @Async
    public void saveLikeBlog(Member member, Blog blog) {
        try {
            MemberLikeBlog memberLikeBlog = new MemberLikeBlog();
            memberLikeBlog.setMember(member);
            memberLikeBlog.setBlog(blog);
            memberLikeBlog.setUpdatedTime(new Date());
            memberLikeBlog.setCreatedBy(member.getNickName());
            create(memberLikeBlog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("save failed!!! /n" + e.getMessage());
        }
    }
}
