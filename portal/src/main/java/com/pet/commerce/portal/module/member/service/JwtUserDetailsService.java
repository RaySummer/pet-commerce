package com.pet.commerce.portal.module.member.service;

import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.service.MemberCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
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

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member existingMember = memberCoreService.findMemberByAccount(account);

        log.info("member is : [{}]", existingMember.getAccount());
        List<GrantedAuthority> roleList = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(existingMember.getAccount(), existingMember.getPassword(),
                roleList);
    }
}
