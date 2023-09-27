package com.pet.commerce.core.module.user.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.user.dto.SysRoleCreateDto;
import com.pet.commerce.core.module.user.dto.SysRoleUpdateDto;
import com.pet.commerce.core.module.user.model.SysRole;
import com.pet.commerce.core.module.user.repository.SysRoleRepository;
import com.pet.commerce.core.utils.CustomizeException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ray
 * @since 2023/2/15
 */
@Service
@Transactional
public class SysRoleCoreService extends BaseCrudServiceImpl<SysRoleRepository, SysRole, Long> {

    public List<SysRole> findAllRoles() {
        return baseRepository.findAllByStatusIsTrueAndDeletedByIsNullOrderByIdAsc();
    }

    public SysRole findByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return baseRepository.findByCodeAndStatusIsTrueAndDeletedByIsNull(code);
    }

    public SysRole createRole(SysRoleCreateDto dto) {
        SysRole sysRole = baseRepository.findByCodeAndStatusIsTrueAndDeletedByIsNull(dto.getCode());
        if (ObjectUtils.isNotEmpty(sysRole)) {
            throw new CustomizeException("This account is existed");
        }
        sysRole = new SysRole();
        sysRole.setCode(dto.getCode());
        sysRole.setName(dto.getName());
        sysRole.setDescription(dto.getDescription());
        sysRole.setStatus(dto.getStatus());
        return create(sysRole);
    }

    public SysRole updateRole(SysRoleUpdateDto dto) {
        SysRole sysRole = baseRepository.findByCodeAndStatusIsTrueAndDeletedByIsNull(dto.getCode());
        if (ObjectUtils.isEmpty(sysRole)) {
            throw new CustomizeException("This account is not exist");
        }
        sysRole.setName(dto.getName());
        sysRole.setDescription(dto.getDescription());
        sysRole.setStatus(dto.getStatus());
        return update(sysRole);
    }

    public void deleteRole(SysRole sysRole) {
        delete(sysRole);
    }


}
