package com.pet.commerce.portal.module.member.service;

import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.service.MemberCoreService;
import com.pet.commerce.core.module.user.model.SysRole;
import com.pet.commerce.core.module.user.model.SysUser;
import com.pet.commerce.core.module.user.model.SysUserRSysRole;
import com.pet.commerce.core.module.user.service.SysUserCoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ray
 * @since 2023/2/16
 */
@Slf4j
@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {

    @Lazy
    @Autowired
    private MemberCoreService memberCoreService;

    @Lazy
    @Autowired
    private SysUserCoreService sysUserCoreService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member existingMember = memberCoreService.findMemberByAccount(account);

        if (ObjectUtils.isNotEmpty(existingMember)) {
            log.info("member is : [{}]", existingMember.getAccount());
            List<GrantedAuthority> roleList = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(existingMember.getAccount(), existingMember.getPassword(),
                    roleList);
        } else {
            SysUser sysUser = sysUserCoreService.findByAccount(account);

            log.info("User is : [{}]", sysUser.getAccount());
            List<GrantedAuthority> roleList = new ArrayList<>();

            for (SysUserRSysRole sysUserRSysRole : sysUser.getSysUserRSysRoles()) {
                SysRole sysRole = sysUserRSysRole.getSysRole();
                roleList.add(new SimpleGrantedAuthority("ROLE_" + sysRole.getCode()));
            }
            return new org.springframework.security.core.userdetails.User(sysUser.getAccount(), sysUser.getPassword(),
                    roleList);
        }
    }
}
