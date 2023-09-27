package com.pet.commerce.core.module.user.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.user.model.SysUser;

/**
 * @author Ray
 * @since 2023/2/15
 */
public interface SysUserRepository extends BaseRepository<SysUser, Long> {

    SysUser findByAccountAndDeletedByIsNull(String account);

}
