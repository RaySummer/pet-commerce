package com.pet.commerce.portal.module.member.service;

import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.service.MemberCoreService;
import com.pet.commerce.core.module.user.model.SysUser;
import com.pet.commerce.core.module.user.service.SysUserCoreService;
import com.pet.commerce.portal.module.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthenticateService
 *
 * @author : ray
 * @since : 1.0 2023/09/27
 **/
@Slf4j
@Service()
@Transactional
public class AuthenticateService {

    @Lazy
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Lazy
    @Autowired
    private MemberCoreService memberCoreService;

    @Lazy
    @Autowired
    private SysUserCoreService sysUserCoreService;

    public UserDetails findUserByJwt(String account, String password) {
        Member member = memberCoreService.findMemberByAccountAndPassword(account, password);
        if (ObjectUtils.isEmpty(member)) {
            SysUser sysUser = sysUserCoreService.findUserByAccountAndPassword(account, password);
            if (ObjectUtils.isEmpty(sysUser)) {
                return null;
            }
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(sysUser.getAccount());
            return userDetails;
        } else {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(member.getAccount());
            return userDetails;
        }
    }

}
