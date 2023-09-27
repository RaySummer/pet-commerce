package com.pet.commerce.core.module.user.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.user.model.SysUserRSysRole;
import com.pet.commerce.core.module.user.repository.SysUserRSysRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ray
 * @since 2023/2/15
 */
@Service
@Transactional
public class SysUserRSysRoleCoreService extends BaseCrudServiceImpl<SysUserRSysRoleRepository, SysUserRSysRole, Long> {

}
