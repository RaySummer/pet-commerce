package com.pet.commerce.portal.module.blog.service;

import com.pet.commerce.core.exception.BusinessException;
import com.pet.commerce.core.module.base.vo.CreateVO;
import com.pet.commerce.core.module.base.vo.DeleteVO;
import com.pet.commerce.core.module.base.vo.PageVO;
import com.pet.commerce.core.module.base.vo.UpdateVO;
import com.pet.commerce.core.module.blog.dto.BlogCreateDto;
import com.pet.commerce.core.module.blog.dto.BlogSearchPageResultDto;
import com.pet.commerce.core.module.blog.dto.BlogUpdateDto;
import com.pet.commerce.core.module.blog.enums.BlogAddCountTypeEnum;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.blog.model.BlogCategory;
import com.pet.commerce.core.module.blog.model.BlogRBlogCategory;
import com.pet.commerce.core.module.blog.model.QBlog;
import com.pet.commerce.core.module.blog.service.BlogCoreService;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.model.MemberCollectBlog;
import com.pet.commerce.core.module.member.model.MemberLikeBlog;
import com.pet.commerce.core.module.member.model.MemberReadHistoryBlog;
import com.pet.commerce.core.module.member.service.MemberCollectBlogCoreService;
import com.pet.commerce.core.module.member.service.MemberLikeBlogCoreService;
import com.pet.commerce.core.module.member.service.MemberReadHistoryBlogCoreService;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.pet.commerce.portal.module.blog.dto.ro.BlogCreateRO;
import com.pet.commerce.portal.module.blog.dto.ro.BlogSearchPageRO;
import com.pet.commerce.portal.module.blog.dto.ro.BlogUpdateRO;
import com.pet.commerce.portal.module.blog.dto.ro.CollectOrLikeBlogSearchPageRO;
import com.pet.commerce.portal.module.blog.dto.vo.BlogGetVO;
import com.pet.commerce.portal.module.blog.dto.vo.BlogSearchPageVO;
import com.pet.commerce.portal.module.member.service.MemberService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * BlogService
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@Slf4j
@Service()
@Transactional
public class BlogService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private BlogCoreService blogCoreService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCollectBlogCoreService memberCollectBlogCoreService;

    @Autowired
    private MemberLikeBlogCoreService memberLikeBlogCoreService;

    @Autowired
    private MemberReadHistoryBlogCoreService memberReadHistoryBlogCoreService;


    public PageVO<BlogSearchPageVO> searchPageByBlog(BlogSearchPageRO ro) {

        QBlog qBlog = QBlog.blog;
//        QBlogTag qBlogTag = QBlogTag.blogTag;
//        QBlogCategory qBlogCategory = QBlogCategory.blogCategory;
//        QBlogRBlogTag qBlogRBlogTag = QBlogRBlogTag.blogRBlogTag;
//        QBlogRBlogCategory qBlogRBlogCategory = QBlogRBlogCategory.blogRBlogCategory;

        BooleanBuilder builder = new BooleanBuilder();

        String keyword = ro.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            builder.andAnyOf(
                    qBlog.title.containsIgnoreCase(keyword),
                    qBlog.content.containsIgnoreCase(keyword)
            );
        }
        String author = ro.getAuthor();
        if (StringUtils.isNotBlank(author)) {
            builder.andAnyOf(
                    qBlog.author.containsIgnoreCase(author)
            );
        }
        String categoryKey = ro.getCategoryKey();
        if (StringUtils.isNotBlank(categoryKey)) {
            builder.and(qBlog.blogRBlogCategories.any().blogCategory.categoryKey.eq(categoryKey));
        }
//        List<String> tagList = ro.getTagList();
//        if (!CollectionUtils.isEmpty(tagList)) {
//            builder.and(qBlog.blogRBlogTags.any().blogTag.content.in(tagList));
//        }
        if (ro.getIsHot()) {
            builder.and(qBlog.isHot.isTrue());
        }
        if (ro.getIsTop()) {
            builder.and(qBlog.isTop.isTrue());
        }
        builder.and(qBlog.deletedBy.isNull());

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);

        query.select(qBlog);
        query.from(qBlog);
//                .leftJoin(qBlogRBlogCategory).on(qBlog.id.eq(qBlogRBlogCategory.blog.id).and(qBlogRBlogCategory.deletedBy.isNull()))
//                .leftJoin(qBlogCategory).on(qBlogRBlogCategory.blogCategory.id.eq(qBlogCategory.id).and(qBlogCategory.deletedBy.isNull()))
//                .leftJoin(qBlogRBlogTag).on(qBlog.id.eq(qBlogRBlogTag.blog.id).and(qBlogRBlogTag.deletedBy.isNull()))
//                .leftJoin(qBlogTag).on(qBlogRBlogTag.blogTag.id.eq(qBlogTag.id).and(qBlogTag.deletedBy.isNull()));


        query.distinct().where(builder);

        //order by
        String sort = ro.getSort();
        ArrayList<OrderSpecifier<?>> sortOrders = new ArrayList<>();


        if (StringUtils.isNotBlank(sort) && sort.equalsIgnoreCase("byView")) {
            query.orderBy(qBlog.readCount.desc()).orderBy(qBlog.publishTime.desc());
            sortOrders.add(qBlog.readCount.desc());
            sortOrders.add(qBlog.publishTime.desc());
        } else if (StringUtils.isNotBlank(sort) && sort.equalsIgnoreCase("byLike")) {
            sortOrders.add(qBlog.likeCount.desc());
            sortOrders.add(qBlog.publishTime.desc());
        } else if (StringUtils.isNotBlank(sort) && sort.equalsIgnoreCase("byComment")) {
            sortOrders.add(qBlog.commentCount.desc());
            sortOrders.add(qBlog.publishTime.desc());
        } else {
            sortOrders.add(qBlog.readCount.desc());
            sortOrders.add(qBlog.likeCount.desc());
            sortOrders.add(qBlog.commentCount.desc());
            sortOrders.add(qBlog.publishTime.desc());
        }

        return generateQueryGetResult(qBlog, ro.getPageable(), builder, sortOrders);
    }

    private PageVO<BlogSearchPageVO> generateQueryGetResult(QBlog qBlog, Pageable pageable, BooleanBuilder builder, ArrayList<OrderSpecifier<?>> sortOrders) {

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

        List<BlogSearchPageVO> voContent = new ArrayList<>();

        results.forEach(blog -> {
            BlogSearchPageVO vo = new BlogSearchPageVO();
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

    private Function<Tuple, BlogSearchPageVO> generateMapper() {

        return tuple -> {
            Blog blog = tuple.get(QBlog.blog);
            BlogSearchPageVO vo = BlogSearchPageVO.builder()
                    .title(blog.getTitle())
                    .coverImage(blog.getCoverImage())
                    .uid(blog.getUidStr())
                    .isHot(blog.isHot())
                    .isTop(blog.isTop())
                    .author(blog.getAuthor())
                    .publishTime(blog.getPublishTime())
                    .likeCount(blog.getLikeCount())
                    .unlikeCount(blog.getUnlikeCount())
                    .readCount(blog.getReadCount())
                    .commentCount(blog.getCommentCount())
                    .build();
//            Set<BlogRBlogTag> blogRBlogTagSet = blog.getBlogRBlogTags();
//            if (!CollectionUtils.isEmpty(blogRBlogTagSet)) {
//                Set<BlogTag> blogTags = new HashSet<>();
//                blogRBlogTagSet.forEach(item -> {
//                    blogTags.add(item.getBlogTag());
//                });
//                vo.getBlogTagList().addAll(blogTags);
//            }
            Set<BlogRBlogCategory> blogRBlogCategories = blog.getBlogRBlogCategories();
            if (!CollectionUtils.isEmpty(blogRBlogCategories)) {
                Set<BlogCategory> blogCategories = new HashSet<>();
                blogRBlogCategories.forEach(item -> {
                    blogCategories.add(item.getBlogCategory());
                });
                vo.getCategories().addAll(blogCategories);
            }

            return vo;
        };
    }

    public BlogGetVO findBlogByUid(String uid) {
        Member member = memberService.findByUid(WebThreadLocal.getMember().getUidStr());
        Blog blog = blogCoreService.judgeThisBlogExists(uid);
        blogCoreService.updateCount(member, blog, BlogAddCountTypeEnum.READ);
        return BlogGetVO.of(blog);
    }

    @Transactional
    public CreateVO createBlog(BlogCreateRO ro) {
        BlogCreateDto dto = new BlogCreateDto();
        dto.setTitle(ro.getTitle());
        dto.setContent(ro.getContent());
        dto.setCoverImage(ro.getCoverImage());
        dto.setBannerImage(ro.getBannerImage());
        dto.setDescription(ro.getDescription());
        dto.setPublishTime(ro.getPublishTime());
        dto.setAllowComment(ro.getAllowComment());
        dto.setType(ro.getType());
        if (!CollectionUtils.isEmpty(ro.getCategories())) {
            dto.getCategories().addAll(ro.getCategories());
        }
//        dto.getTags().addAll(ro.getTags());
        if (!CollectionUtils.isEmpty(ro.getMedias())) {
            dto.getMedias().addAll(ro.getMedias());
        }

        return blogCoreService.createBlog(dto);
    }

    @Transactional
    public UpdateVO modifyBlog(BlogUpdateRO ro, String uid) {
        if (StringUtils.isBlank(uid)) {
            throw new BusinessException("UUID is null");
        }

        BlogUpdateDto dto = new BlogUpdateDto();
        if (StringUtils.isNotBlank(ro.getContent())) {
            dto.setContent(ro.getContent());
        }
        if (StringUtils.isNotBlank(ro.getBannerImage())) {
            dto.setBannerImage(ro.getBannerImage());
        }
        if (StringUtils.isNotBlank(ro.getCoverImage())) {
            dto.setCoverImage(ro.getCoverImage());
        }
        dto.getCategories().addAll(ro.getCategories());
//        dto.getTags().addAll(ro.getTags());
        dto.getMediaLinks().addAll(ro.getMediaLinks());

        return blogCoreService.updateBlog(uid, dto);
    }

    @Transactional
    public DeleteVO delBlog(String uid) {
        return blogCoreService.deleteBlog(uid);
    }

    public void likeOrUnlikeBlog(String uid, String type) {
        Blog blog = blogCoreService.judgeThisBlogExists(uid);
        System.out.println(Thread.currentThread().getId());
        Member member = memberService.findByUid(WebThreadLocal.getMember().getUidStr());
        if (type.equalsIgnoreCase("like")) {
            blogCoreService.updateCount(member, blog, BlogAddCountTypeEnum.LIKE);
        } else if (type.equalsIgnoreCase("unlike")) {
            blogCoreService.updateCount(member, blog, BlogAddCountTypeEnum.UNLIKE);
        }
    }

    public void collectBlog(String uid) {
        Blog blog = blogCoreService.judgeThisBlogExists(uid);
        Member member = memberService.findByUid(WebThreadLocal.getMember().getUidStr());

        MemberCollectBlog memberCollectBlog = new MemberCollectBlog();
        memberCollectBlog.setBlog(blog);
        memberCollectBlog.setMember(member);
        memberCollectBlog.setCreatedBy(member.getNickName());
        memberCollectBlog.setCreatedTime(new Date());
        memberCollectBlogCoreService.create(memberCollectBlog);
    }

    public void unCollectBlog(String blogUid) {
        Blog blog = blogCoreService.findByUid(blogUid);
        Member member = memberService.findByUid(WebThreadLocal.getMember().getUidStr());
        memberCollectBlogCoreService.deleteCollect(member, blog);
    }

    public PageVO<BlogSearchPageVO> searchPageByType(CollectOrLikeBlogSearchPageRO ro, String type) {

        List<Long> blogIds = getBlogIdsByCollectOrLike(type);

        if (CollectionUtils.isEmpty(blogIds)) {
            return new PageVO<>();
        }

        QBlog qBlog = QBlog.blog;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qBlog.id.in(blogIds));

        String keyword = ro.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            builder.andAnyOf(
                    qBlog.title.containsIgnoreCase(keyword),
                    qBlog.content.containsIgnoreCase(keyword)
            );
        }
        String author = ro.getAuthor();
        if (StringUtils.isNotBlank(author)) {
            builder.andAnyOf(
                    qBlog.author.containsIgnoreCase(author)
            );
        }
        String categoryKey = ro.getCategoryKey();
        if (StringUtils.isNotBlank(categoryKey)) {
            builder.and(qBlog.blogRBlogCategories.any().blogCategory.categoryKey.eq(categoryKey));
        }

        builder.and(qBlog.deletedBy.isNull());

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);

        query.select(qBlog);
        query.from(qBlog);

        query.distinct().where(builder);

        //order by
        String sort = ro.getSort();
        ArrayList<OrderSpecifier<?>> sortOrders = new ArrayList<>();

        return generateQueryGetResult(qBlog, ro.getPageable(), builder, sortOrders);
    }

    private List<Long> getBlogIdsByCollectOrLike(String type) {
        Member member = memberService.findByUid(WebThreadLocal.getMember().getUidStr());
        List<Long> list = new ArrayList<>();

        if (type.equalsIgnoreCase("collect")) {
            List<MemberCollectBlog> memberCollectBlogList = memberCollectBlogCoreService.findByMember(member);

            if (CollectionUtils.isEmpty(memberCollectBlogList)) {
                return null;
            }

            for (MemberCollectBlog memberCollectBlog : memberCollectBlogList) {
                Long id = memberCollectBlog.getBlog().getId();
                list.add(id);
            }
        } else if (type.equalsIgnoreCase("like")) {
            List<MemberLikeBlog> memberLikeBlogList = memberLikeBlogCoreService.findByMember(member);

            if (CollectionUtils.isEmpty(memberLikeBlogList)) {
                return null;
            }

            for (MemberLikeBlog memberLikeBlog : memberLikeBlogList) {
                Long id = memberLikeBlog.getBlog().getId();
                list.add(id);
            }
        } else if (type.equalsIgnoreCase("history")) {
            List<MemberReadHistoryBlog> memberReadHistoryBlogList = memberReadHistoryBlogCoreService.findByMember(member);

            if (CollectionUtils.isEmpty(memberReadHistoryBlogList)) {
                return null;
            }

            for (MemberReadHistoryBlog memberReadHistoryBlog : memberReadHistoryBlogList) {
                Long id = memberReadHistoryBlog.getBlog().getId();
                list.add(id);
            }
        }
        return list;
    }
}
