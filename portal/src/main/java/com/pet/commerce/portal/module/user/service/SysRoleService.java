package com.pet.commerce.portal.module.user.service;

import com.pet.commerce.core.module.user.dto.SysRoleCreateDto;
import com.pet.commerce.core.module.user.dto.SysRoleUpdateDto;
import com.pet.commerce.core.module.user.model.SysRole;
import com.pet.commerce.core.module.user.service.SysRoleCoreService;
import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.portal.module.user.dto.ro.SysRoleRO;
import com.pet.commerce.portal.module.user.dto.vo.SysRoleDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * SysRoleService
 *
 * @author : ray
 * @since : 1.0 2023/09/26
 **/
@Slf4j
@Service
@Transactional
public class SysRoleService {

    @Autowired
    private SysRoleCoreService sysRoleCoreService;

    public SysRoleCreateDto createRole(SysRoleRO ro) throws CustomizeException {
        if (StringUtils.isBlank(ro.getCode())) {
            throw new CustomizeException("Role Code Cannot be null");
        }
        SysRoleCreateDto dto = new SysRoleCreateDto();
        dto.setName(ro.getName());
        dto.setCode(ro.getCode());
        dto.setDescription(ro.getDescription());
        dto.setStatus(ro.getStatus());
        sysRoleCoreService.createRole(dto);
        return dto;
    }

    public SysRoleUpdateDto modifyRole(String uid, SysRoleRO ro) {
        SysRole sysRole = sysRoleCoreService.findByUid(uid);
        if (ObjectUtils.isEmpty(sysRole)) {
            throw new CustomizeException("Cannot found role");
        }
        SysRoleUpdateDto dto = new SysRoleUpdateDto();
        dto.setCode(sysRole.getCode());
        dto.setName(ro.getName());
        dto.setDescription(ro.getDescription());
        dto.setStatus(ro.getStatus());
        sysRoleCoreService.updateRole(dto);
        return dto;
    }

    public void deleteRole(String uid) {
        SysRole sysRole = sysRoleCoreService.findByUid(uid);
        if (ObjectUtils.isEmpty(sysRole)) {
            throw new CustomizeException("Cannot found role");
        }
        sysRoleCoreService.deleteRole(sysRole);
    }

    public List<SysRoleDetailVO> findAllRoles() {
        List<SysRole> sysRoleList = sysRoleCoreService.findAllRoles();
        List<SysRoleDetailVO> sysRoleDetailVOList = new ArrayList<>();
        sysRoleList.forEach(sysRole -> {
            SysRoleDetailVO vo = new SysRoleDetailVO();
            vo.setCode(sysRole.getCode());
            vo.setName(sysRole.getName());
            vo.setDescription(sysRole.getDescription());
            vo.setStatus(sysRole.isStatus());
            sysRoleDetailVOList.add(vo);
        });
        return sysRoleDetailVOList;
    }

    public SysRoleDetailVO findOneByCode(String code) {
        SysRole sysRole = sysRoleCoreService.findByCode(code);
        if (ObjectUtils.isEmpty(sysRole)) {
            throw new CustomizeException("Cannot found role");
        }
        SysRoleDetailVO vo = new SysRoleDetailVO();
        vo.setName(sysRole.getName());
        vo.setCode(sysRole.getCode());
        vo.setDescription(sysRole.getDescription());
        vo.setStatus(sysRole.isStatus());
        return vo;
    }
}
