//package com.pet.commerce.core.module.blog.dto;
//
//import com.pet.commerce.core.module.blog.model.BlogRBlogTag;
//import com.pet.commerce.core.module.blog.model.BlogTag;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.SuperBuilder;
//import org.apache.commons.lang3.ObjectUtils;
//
//import java.io.Serializable;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * BlogTagGetVO
// *
// * @author : ray
// * @since : 1.0 2023/09/18
// **/
//@SuperBuilder
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class BlogTagDetailDto implements Serializable {
//    private static final long serialVersionUID = -8721425162014910343L;
//
//    private String uuid;
//
//    private String content;
//
//    private Boolean recommend = Boolean.FALSE;
//
//    private Boolean active = Boolean.FALSE;
//
//    public static BlogTagDetailDto of(BlogTag blogTag) {
//        if (ObjectUtils.isEmpty(blogTag)) {
//            return null;
//        }
//        BlogTagDetailDto vo = new BlogTagDetailDto();
//        vo.setUuid(blogTag.getUidStr());
//        vo.setContent(blogTag.getContent());
//        vo.setRecommend(blogTag.getRecommend());
//        vo.setActive(blogTag.getActive());
//        return vo;
//    }
//
//    public static Set<BlogTagDetailDto> ofList(Set<BlogRBlogTag> blogRBlogTagSet) {
//        if (blogRBlogTagSet.isEmpty()) {
//            return null;
//        }
//        return blogRBlogTagSet.stream().map(blogRBlogTag -> of(blogRBlogTag.getBlogTag())).collect(Collectors.toSet());
//    }
//
//}
