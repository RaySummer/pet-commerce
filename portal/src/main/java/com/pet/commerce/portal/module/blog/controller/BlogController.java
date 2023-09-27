package com.pet.commerce.portal.module.blog.controller;

import com.pet.commerce.core.module.base.vo.PageVO;
import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.core.utils.Response;
import com.pet.commerce.portal.module.blog.dto.ro.BlogCreateRO;
import com.pet.commerce.portal.module.blog.dto.ro.BlogSearchPageRO;
import com.pet.commerce.portal.module.blog.dto.ro.BlogUpdateRO;
import com.pet.commerce.portal.module.blog.dto.ro.CollectOrLikeBlogSearchPageRO;
import com.pet.commerce.portal.module.blog.dto.vo.BlogSearchPageVO;
import com.pet.commerce.portal.module.blog.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BlogController
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@Slf4j
@RestController
@RequestMapping({"/blog"})
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/page")
    public PageVO<BlogSearchPageVO> searchPageByBlog(@RequestBody BlogSearchPageRO ro) {
        return blogService.searchPageByBlog(ro);
    }

    @GetMapping("/{uid}")
    public Response blogDetail(@PathVariable String uid) {
        try {
            return Response.of(blogService.findBlogByUid(uid));
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @PostMapping("/create")
    public Response createBlog(@RequestBody BlogCreateRO ro) {
        try {
            return Response.of(blogService.createBlog(ro));
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @PutMapping("/modify/{uid}")
    public Response modifyBlog(@RequestBody BlogUpdateRO ro, @PathVariable String uid) {
        try {
            return Response.of(blogService.modifyBlog(ro, uid));
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @DeleteMapping("/del/{uid}")
    public Response delBlog(@PathVariable String uid) {
        try {
            blogService.delBlog(uid);
            return Response.of("删除成功");
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @PostMapping("/like/{uid}")
    public Response likeBlog(@PathVariable String uid) {
        try {
            blogService.likeOrUnlikeBlog(uid, "like");
            return Response.of("点赞帖子成功");
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.of("点赞帖子失败");
        }
    }

    @PostMapping("/unlike/{uid}")
    public Response unlikeBlog(@PathVariable String uid) {
        try {
            blogService.likeOrUnlikeBlog(uid, "unlike");
            return Response.of("踩帖子成功");
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.of("踩帖子失败");
        }
    }

    @PostMapping("/collect/{uid}")
    public Response collectBlog(@PathVariable String uid) {
        try {
            blogService.collectBlog(uid);
            return Response.of("收藏成功");
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.of("收藏失败");
        }
    }

    @PostMapping("/un-collect/{uid}")
    public Response unCollectBlog(@PathVariable String uid) {
        try {
            blogService.unCollectBlog(uid);
            return Response.of("取消收藏成功");
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.of("取消收藏失败");
        }
    }

    @PostMapping("/collect/page")
    public PageVO<BlogSearchPageVO> searchPageByCollectBlog(@RequestBody CollectOrLikeBlogSearchPageRO ro) {
        return blogService.searchPageByType(ro, "collect");
    }

    @PostMapping("/like/page")
    public PageVO<BlogSearchPageVO> searchPageByLikeBlog(@RequestBody CollectOrLikeBlogSearchPageRO ro) {
        return blogService.searchPageByType(ro, "like");
    }

    @PostMapping("/history/page")
    public PageVO<BlogSearchPageVO> searchPageByHistoryBlog(@RequestBody CollectOrLikeBlogSearchPageRO ro) {
        return blogService.searchPageByType(ro, "history");
    }

}
