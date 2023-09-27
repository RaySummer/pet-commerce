package com.pet.commerce.core.module.media.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.blog.model.Blog;
import com.pet.commerce.core.module.blog.repository.BlogRepository;
import com.pet.commerce.core.module.media.dto.MediaDto;
import com.pet.commerce.core.module.media.dto.MediaUpdateDto;
import com.pet.commerce.core.module.media.model.Media;
import com.pet.commerce.core.module.media.model.QMedia;
import com.pet.commerce.core.module.media.repository.MediaRepository;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.repository.MemberRepository;
import com.pet.commerce.core.module.user.model.SysUser;
import com.pet.commerce.core.module.user.repository.SysUserRepository;
import com.pet.commerce.core.utils.CustomizeException;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MediasCoreService
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@Slf4j
@Service
@Transactional
public class MediaCoreService extends BaseCrudServiceImpl<MediaRepository, Media, Long> {

    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SysUserRepository sysUserRepository;

    @Async
    public List<Media> saveMedias(List<MediaDto> mediaDtoList) {
        if (CollectionUtils.isEmpty(mediaDtoList)) {
            throw new CustomizeException("保存失败, 没有数据");
        }
        List<Media> medias = new ArrayList<>();
        for (MediaDto dto : mediaDtoList) {
            Media media = new Media();
            media.setType(dto.getType());
            media.setSize(dto.getSize());
            media.setOriginal(dto.getOriginal());
            media.setReduce(dto.getReduce());
            media.setThumbnail(dto.getThumbnail());
            medias.add(media);
        }
        if (CollectionUtils.isEmpty(medias)) {
            throw new CustomizeException("保存失败, 没有数据");
        }
        return save(medias);
    }

    @Async
    public void updateMedias(List<String> mediaLinks, MediaUpdateDto dto) {
        if (CollectionUtils.isEmpty(mediaLinks)) {
            throw new CustomizeException("没有数据");
        }
        for (String link : mediaLinks) {
            Media media = findByLink(link);
            if (ObjectUtils.isEmpty(media)) {
                log.error("{} , 找不到数据", link);
            }
            if (StringUtils.isNotBlank(dto.getBlogUid())) {
                Blog blog = blogRepository.findOneByUidAndDeletedTimeNull(dto.getBlogUid());
                media.setBlog(blog);
            }
            //todo: 产品的
//            if (StringUtils.isNotBlank(dto.getProductUid())) {
//                Blog blog = blogRepository.findOneByUidAndDeletedTimeNull(dto.getBlogUid());
//                media.setBlog(blog);
//            }
            if (StringUtils.isNotBlank(dto.getMemberUid())) {
                Member member = memberRepository.findOneByUidAndDeletedTimeNull(dto.getMemberUid());
                media.setMember(member);
            }
            if (StringUtils.isNotBlank(dto.getBlogUid())) {
                SysUser sysUser = sysUserRepository.findOneByUidAndDeletedTimeNull(dto.getUserUid());
                media.setSysUser(sysUser);
            }
            update(media);
        }
    }

    public Media findByLink(String link) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.andAnyOf(
                QMedia.media.original.eq(link),
                QMedia.media.reduce.eq(link),
                QMedia.media.thumbnail.eq(link)
        );
        builder.and(QMedia.media.deletedBy.isNull());
        Optional<Media> one = mediaRepository.findOne(builder);
        return one.orElse(null);
    }

}
