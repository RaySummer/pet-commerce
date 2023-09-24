package com.pet.commerce.portal.module.blog.controller;

import com.pet.commerce.core.module.base.dto.WebThreadLocalDto;
import com.pet.commerce.core.module.base.vo.CreateVO;
import com.pet.commerce.core.module.base.vo.DeleteVO;
import com.pet.commerce.core.module.base.vo.PageVO;
import com.pet.commerce.core.module.base.vo.Result;
import com.pet.commerce.core.module.base.vo.UpdateVO;
import com.pet.commerce.core.module.member.dto.MemberDto;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.pet.commerce.portal.module.blog.dto.ro.BlogCreateRO;
import com.pet.commerce.portal.module.blog.dto.ro.BlogSearchPageRO;
import com.pet.commerce.portal.module.blog.dto.ro.BlogUpdateRO;
import com.pet.commerce.portal.module.blog.dto.ro.CollectOrLikeBlogSearchPageRO;
import com.pet.commerce.portal.module.blog.dto.vo.BlogGetVO;
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
    public BlogGetVO blogDetail(@PathVariable String uid) {
        return blogService.findBlogByUid(uid);
    }

    @PostMapping("/create")
    public CreateVO createBlog(@RequestBody BlogCreateRO ro) {
        return blogService.createBlog(ro);
    }

    @PutMapping("/modify/{uid}")
    public UpdateVO modifyBlog(@RequestBody BlogUpdateRO ro, @PathVariable String uid) {
        return blogService.modifyBlog(ro, uid);
    }

    @DeleteMapping("/del/{uid}")
    public DeleteVO delBlog(@PathVariable String uid) {
        return blogService.delBlog(uid);
    }

    @PostMapping("/like/{uid}")
    public Result likeBlog(@PathVariable String uid) {
        try {
            blogService.likeOrUnlikeBlog(uid, "like");
            return Result.ok("点赞帖子成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("点赞帖子失败");
        }
    }

    @PostMapping("/unlike/{uid}")
    public Result unlikeBlog(@PathVariable String uid) {
        try {
            blogService.likeOrUnlikeBlog(uid, "unlike");
            return Result.ok("踩帖子成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("踩帖子失败");
        }
    }

    @PostMapping("/collect/{uid}")
    public Result collectBlog(@PathVariable String uid) {
        try {
            blogService.collectBlog(uid);
            return Result.ok("收藏成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("收藏失败");
        }
    }

    @PostMapping("/un-collect/{uid}")
    public Result unCollectBlog(@PathVariable String uid) {
        try {
            blogService.unCollectBlog(uid);
            return Result.ok("取消收藏成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("取消收藏失败");
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
