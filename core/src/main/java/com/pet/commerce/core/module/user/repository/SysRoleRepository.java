package com.pet.commerce.core.module.user.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.user.model.SysRole;

import java.util.List;

/**
 * @author Ray
 * @since 2023/2/15
 */
public interface SysRoleRepository extends BaseRepository<SysRole, Long> {

    SysRole findByCodeAndStatusIsTrueAndDeletedByIsNull(String code);

    List<SysRole> findAllByStatusIsTrueAndDeletedByIsNullOrderByIdAsc();
}
