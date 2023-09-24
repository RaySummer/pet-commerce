package com.pet.commerce.core.module.member.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.member.model.PlatformTask;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @since 2023/3/26
 */
public interface PlatformTaskRepository extends BaseRepository<PlatformTask, Long> {

    @Query(value = "select pt from PlatformTask pt where pt.expireTime > :#{#expireTime}")
    List<PlatformTask> findAllByExpireTimeBefore(Date expireTime);
}
