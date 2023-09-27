package com.pet.commerce.core.module.blog.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.base.vo.PageVO;
import com.pet.commerce.core.module.blog.dto.BlogCreateDto;
import com.pet.commerce.core.module.blog.dto.BlogDetailDto;
import com.pet.commerce.core.module.blog.dto.BlogSearchPageParamDto;
import com.pet.commerce.core.module.blog.dto.BlogSearchPageResultDto;
import com.pet.commerce.core.module.blog.dto.BlogUpdateDto;
import com.pet.commerce.core.module.blog.enums.BlogAddCountTypeEnum;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.blog.model.BlogCategory;
import com.pet.commerce.core.module.blog.model.BlogRBlogCategory;
import com.pet.commerce.core.module.blog.model.QBlog;
import com.pet.commerce.core.module.blog.repository.BlogRepository;
import com.pet.commerce.core.module.media.model.Media;
import com.pet.commerce.core.module.media.service.MediaCoreService;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.service.MemberCoreService;
import com.pet.commerce.core.module.member.service.MemberLikeBlogCoreService;
import com.pet.commerce.core.module.member.service.MemberReadHistoryBlogCoreService;
import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.core.utils.LockHelper;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.pet.commerce.core.constants.Constants.PLATFORM_ACCOUNT;
import static com.pet.commerce.core.constants.Constants.PLATFORM_ACCOUNT_MOBILE_NUMBER;

/**
 * BlogService
 *
 * @author : ray
 * @since : 1.0 2023/09/18
 **/
@Slf4j
@Service
@Transactional
public class BlogCoreService extends BaseCrudServiceImpl<BlogRepository, Blog, Long> {

    @Autowired
    private LockHelper lockHelper;

    @Autowired
    private MemberCoreService memberCoreService;

    @Autowired
    private BlogCategoryCoreService blogCategoryCoreService;

//    @Autowired
//    private BlogTagCoreService blogTagCoreService;

    @Autowired
    private MediaCoreService mediaCoreService;

    @Autowired
    private MemberLikeBlogCoreService memberLikeBlogCoreService;

    @Autowired
    private MemberReadHistoryBlogCoreService memberReadHistoryBlogCoreService;

    @Transactional
    public Blog createBlog(BlogCreateDto dto) {
        //先保存基本数据
        Blog blog = new Blog();
        blog.setTitle(dto.getTitle());
        blog.setContent(dto.getContent());
        blog.setCoverImage(dto.getCoverImage());
        blog.setBannerImage(dto.getBannerImage());
        blog.setDescription(dto.getDescription());
        blog.setHot(dto.getIsHot());
        blog.setTop(dto.getIsTop());
        blog.setCommentNeedReview(dto.getCommentNeedReview());
        blog.setPublishTime(dto.getPublishTime());
        blog.setAllowComment(dto.getAllowComment());

        Member member;
        if (WebThreadLocal.getMember() != null) {
            member = memberCoreService.findByUid(WebThreadLocal.getMember().getUid());
        } else {
            member = memberCoreService.findPlatformMember(PLATFORM_ACCOUNT, PLATFORM_ACCOUNT_MOBILE_NUMBER);
        }
        if (member == null) {
            throw new CustomizeException("Filed! Cannot found member!!!");
        }
        blog.setMember(member);
        blog.setAuthor(member.getNickName());

        create(blog);

        //保存图片
        if (CollectionUtils.isNotEmpty(dto.getMedias())) {
            List<Media> mediaList = dto.getMedias().stream().map(link -> mediaCoreService.findByLink(link)).collect(Collectors.toList());
            blog.getMediaSet().addAll(mediaList);
            create(blog);
        }

        //保存类目数据
        if (CollectionUtils.isNotEmpty(dto.getCategories())) {
            List<BlogCategory> blogCategories = blogCategoryCoreService.findCategoriesByKeys(dto.getCategories());
            List<BlogRBlogCategory> blogRBlogCategories = new ArrayList<>();
            blogCategories.forEach(item -> {
                BlogRBlogCategory blogRBlogCategory = new BlogRBlogCategory();
                blogRBlogCategory.setBlog(blog);
                blogRBlogCategory.setBlogCategory(item);
                blogRBlogCategory.setCreatedBy(WebThreadLocal.getOperatorName());
                blogRBlogCategories.add(blogRBlogCategory);
            });
            blog.getBlogRBlogCategories().addAll(blogRBlogCategories);
            create(blog);
        }

//        //保存标签数据
//        if (CollectionUtils.isNotEmpty(dto.getTags())) {
//            List<BlogTag> blogTagList = blogTagCoreService.findOrCreateTagsByName(dto.getTags());
//            List<BlogRBlogTag> blogRBlogTags = new ArrayList<>();
//            blogTagList.forEach(item -> {
//                BlogRBlogTag blogRBlogTag = new BlogRBlogTag();
//                blogRBlogTag.setBlog(blog);
//                blogRBlogTag.setBlogTag(item);
//                blogRBlogTag.setCreatedBy(WebThreadLocal.getOperatorName());
//                blogRBlogTags.add(blogRBlogTag);
//            });
//            blog.getBlogRBlogTags().addAll(blogRBlogTags);
//            create(blog);
//        }

        return blog;
    }

    public PageVO<BlogSearchPageResultDto> searchBlog(BlogSearchPageParamDto blogSearchPageParamDto) {

        QBlog qBlog = QBlog.blog;

        BooleanBuilder builder = new BooleanBuilder();

        String keyword = blogSearchPageParamDto.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            builder.andAnyOf(
                    qBlog.title.containsIgnoreCase(keyword),
                    qBlog.content.containsIgnoreCase(keyword)
            );
        }
        builder.and(qBlog.active.isTrue());
        builder.and(qBlog.deletedBy.isNull());
        //order by
        ArrayList<OrderSpecifier<?>> sortOrders = new ArrayList<>();

        String sortKey = blogSearchPageParamDto.getSort();
        if (StringUtils.isNotBlank(sortKey)) {
            if (sortKey.equalsIgnoreCase("byLike")) {
                sortOrders.add(qBlog.likeCount.desc());
            } else if (sortKey.equalsIgnoreCase("byRead")) {
                sortOrders.add(qBlog.readCount.desc());
            } else if (sortKey.equalsIgnoreCase("byUnlike")) {
                sortOrders.add(qBlog.unlikeCount.desc());
            } else if (sortKey.equalsIgnoreCase("byComment")) {
                sortOrders.add(qBlog.commentCount.desc());
            }
        }
        sortOrders.add(qBlog.createdTime.desc());

        Pageable pageable = blogSearchPageParamDto.getPageable();

        QueryResults<Blog> tupleQueryResults = new JPAQueryFactory(entityManager)
                .select(qBlog)
                .from(qBlog)
                .where(builder)
                .orderBy(sortOrders.toArray(new OrderSpecifier[sortOrders.size()]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Blog> results = tupleQueryResults.getResults();

        PageImpl<Blog> blogPage = new PageImpl<>(results, pageable, tupleQueryResults.getTotal());

        List<BlogSearchPageResultDto> voContent = new ArrayList<>();

        results.forEach(blog -> {
            BlogSearchPageResultDto vo = new BlogSearchPageResultDto();
            vo.setActive(blog.isActive());
            vo.setUid(blog.getUidStr());
            vo.setCreatedTime(blog.getCreatedTime());
            vo.setUpdatedTime(blog.getUpdatedTime());
            vo.setPublishTime(blog.getPublishTime());
            vo.setAuthor(blog.getMember().getNickName());
            vo.setTitle(blog.getTitle());
            vo.setCoverImage(blog.getCoverImage());
            vo.setIsTop(blog.isTop());
            vo.setIsHot(blog.isHot());
            vo.setLikeCount(blog.getLikeCount());
            vo.setUnlikeCount(blog.getUnlikeCount());
            vo.setReadCount(blog.getReadCount());
            vo.setCommentCount(blog.getCommentCount());
            voContent.add(vo);
        });

        return PageVO.convert(blogPage, voContent);
    }

    public Blog updateBlog(String blogUid, BlogUpdateDto dto) {
        Blog blog = judgeThisBlogExists(blogUid);

        BlogUpdateDto.convertRoToEntity(dto, blog);

        update(blog);

        //保存类目数据
        if (CollectionUtils.isNotEmpty(dto.getCategories())) {
            List<BlogCategory> blogCategories = blogCategoryCoreService.findCategoriesByKeys(dto.getCategories());
            List<BlogRBlogCategory> blogRBlogCategories = new ArrayList<>();
            Blog finalBlog = blog;
            blogCategories.forEach(item -> {
                BlogRBlogCategory blogRBlogCategory = new BlogRBlogCategory();
                blogRBlogCategory.setBlog(finalBlog);
                blogRBlogCategory.setBlogCategory(item);
                blogRBlogCategory.setCreatedBy(WebThreadLocal.getOperatorName());
                blogRBlogCategories.add(blogRBlogCategory);
            });
            blog.getBlogRBlogCategories().addAll(blogRBlogCategories);
            update(blog);
        }

//        //保存标签数据
//        if (CollectionUtils.isNotEmpty(dto.getTags())) {
//            List<BlogTag> blogTagList = blogTagCoreService.findOrCreateTagsByName(dto.getTags());
//            List<BlogRBlogTag> blogRBlogTags = new ArrayList<>();
//            Blog finalBlog1 = blog;
//            blogTagList.forEach(item -> {
//                BlogRBlogTag blogRBlogTag = new BlogRBlogTag();
//                blogRBlogTag.setBlog(finalBlog1);
//                blogRBlogTag.setBlogTag(item);
//                blogRBlogTag.setCreatedBy(WebThreadLocal.getOperatorName());
//                blogRBlogTags.add(blogRBlogTag);
//            });
//            blog.getBlogRBlogTags().addAll(blogRBlogTags);
//            update(blog);
//        }

        //保存图片数据
        if (CollectionUtils.isNotEmpty(dto.getMediaLinks())) {
            List<Media> mediaList = dto.getMediaLinks().stream().map(link -> mediaCoreService.findByLink(link)).collect(Collectors.toList());
            blog.getMediaSet().addAll(mediaList);
            update(blog);
        }

        return blog;
    }

    public BlogDetailDto getBlog(String blogUid) {
        Blog blog = judgeThisBlogExists(blogUid);
        return BlogDetailDto.of(blog);
    }

    public void deleteBlog(String blogUid) {
        Blog blog = judgeThisBlogExists(blogUid);
        delete(blog);
    }

    @Async
    @Transactional
    public void updateCount(Member member, Blog blog, BlogAddCountTypeEnum type) {
        RLock lock = lockHelper.getLock(LockHelper.LOCK_BLOG_UPDATE_COUNT, blog.getUidStr());
        try {
            lock.lock();
            switch (type) {
                case READ:
                    Integer readCount = blog.getReadCount();
                    blog.setReadCount(readCount + 1);
                    memberReadHistoryBlogCoreService.saveLikeBlog(member, blog);
                    break;
                case LIKE:
                    Integer likeCount = blog.getLikeCount();
                    blog.setLikeCount(likeCount + 1);
                    memberLikeBlogCoreService.saveLikeBlog(member, blog);
                    break;
                case UNLIKE:
                    Integer unlikeCount = blog.getUnlikeCount();
                    blog.setUnlikeCount(unlikeCount + 1);
                    break;
                case COMMENT:
                    Integer commentCount = blog.getCommentCount();
                    blog.setCommentCount(commentCount + 1);
                    break;
                default:
                    throw new CustomizeException("没有该类型的数值累加");
            }
            update(blog);
        } finally {
            lock.unlock();
        }
    }

    public Blog judgeThisBlogExists(String uid) {
        if (StringUtils.isBlank(uid)) {
            throw new CustomizeException("UUID is null");
        }
        Blog blog = findByUid(uid);
        if (ObjectUtils.isEmpty(blog)) {
            throw new CustomizeException("Cannot found blog by the uid");
        }
        return blog;
    }

}
