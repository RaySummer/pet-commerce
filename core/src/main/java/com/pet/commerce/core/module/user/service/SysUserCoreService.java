package com.pet.commerce.core.module.user.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.user.dto.SysUserCreateDto;
import com.pet.commerce.core.module.user.dto.SysUserUpdatePasswordDto;
import com.pet.commerce.core.module.user.model.SysRole;
import com.pet.commerce.core.module.user.model.SysUser;
import com.pet.commerce.core.module.user.model.SysUserRSysRole;
import com.pet.commerce.core.module.user.repository.SysUserRepository;
import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.core.utils.WebThreadLocal;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @since 2023/2/15
 */
@Service
@Transactional
public class SysUserCoreService extends BaseCrudServiceImpl<SysUserRepository, SysUser, Long> {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private SysRoleCoreService sysRoleCoreService;

    public SysUser findByAccount(String account) {
        return baseRepository.findByAccountAndDeletedByIsNull(account);
    }

    public SysUser createUser(SysUserCreateDto dto) {
        SysUser sysUser = baseRepository.findByAccountAndDeletedByIsNull(dto.getAccount());
        if (ObjectUtils.isNotEmpty(sysUser)) {
            throw new CustomizeException("This account is existed");
        }
        sysUser = new SysUser();
        sysUser.setEmail(dto.getEmail());

        if (StringUtils.isBlank(dto.getAccount())) {
            sysUser.setAccount(dto.getEmail());
        } else {
            sysUser.setAccount(dto.getAccount());
        }
        sysUser.setAvatar(dto.getAvatar());
        sysUser.setName(dto.getName());
        sysUser.setMobileNumber(dto.getMobileNumber());
        sysUser.setPassword(bcryptEncoder.encode(dto.getPassword()));

        if (!CollectionUtils.isEmpty(dto.getRoleCodes())) {
            List<SysUserRSysRole> sysUserRSysRoleList = new ArrayList<>();
            for (String str : dto.getRoleCodes()) {
                SysRole sysRole = sysRoleCoreService.findByCode(str);
                SysUserRSysRole sysUserRSysRole = new SysUserRSysRole();
                sysUserRSysRole.setSysRole(sysRole);
                sysUserRSysRole.setSysUser(sysUser);
                sysUserRSysRole.setCreatedBy(WebThreadLocal.getOperatorName());
                sysUserRSysRole.setCreatedTime(new Date());
                sysUserRSysRoleList.add(sysUserRSysRole);
            }
            sysUser.getSysUserRSysRoles().clear();
            create(sysUser);
            sysUser.getSysUserRSysRoles().addAll(sysUserRSysRoleList);
        }

        return create(sysUser);
    }

    public SysUser updateUserProfile(SysUser sysUser) {
        return update(sysUser);
    }

    public void deleteUser(SysUser sysUser) {
        delete(sysUser);
    }

    public SysUser updatePassword(SysUserUpdatePasswordDto dto) {
        SysUser sysUser = baseRepository.findByAccountAndDeletedByIsNull(dto.getAccount());
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustomizeException("This account is not exist");
        }
        //暂时关闭校验
        if (!bcryptEncoder.matches(dto.getOldPassword(), sysUser.getPassword())) {
            throw new CustomizeException("The user old password not matches ");
        }
        sysUser.setPassword(bcryptEncoder.encode(dto.getNewPassword()));
        return update(sysUser);
    }

    public SysUser findUserByAccountAndPassword(String account, String password) {
        SysUser sysUser = baseRepository.findByAccountAndDeletedByIsNull(account);
        if (ObjectUtils.isNotEmpty(sysUser)) {
            if (bcryptEncoder.matches(password, sysUser.getPassword())) {
                return sysUser;
            }
        }
        return null;
    }

}
