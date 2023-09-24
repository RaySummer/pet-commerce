package com.pet.commerce.portal.module.member.service;

import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.service.MemberCoreService;
import com.pet.commerce.portal.module.member.dto.ro.MemberRO;
import com.pet.commerce.portal.module.member.dto.ro.MemberRegisterRO;
import com.pet.commerce.portal.module.member.dto.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ray
 * @since 2023/2/16
 */

@Slf4j
@Service()
@Transactional
public class MemberService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private MemberCoreService memberCoreService;

    public boolean checkMemberExists(String userName, String tokenAccount) {

        return false;
    }

    public MemberVO getMemberVOByAccount(String account) {
        return MemberVO.of(memberCoreService.findMemberByAccount(account));
    }

    public Member getMemberByAccount(String account) {
        return memberCoreService.findMemberByAccount(account);
    }

    @Transactional
    public MemberVO register(MemberRegisterRO ro) {
        Member member = new Member();
        member.setAccount(ro.getUsername());
        member.setPassword(bcryptEncoder.encode(ro.getPassword()));
        member.setNickName("新用户：" + ro.getUsername());
        memberCoreService.create(member);
        return MemberVO.of(member);
    }

    @Transactional
    public Member modify(MemberRO ro) {
        Member member = new Member();
        BeanUtils.copyProperties(ro, member);
        memberCoreService.update(member);
        return member;
    }

    public Member findMemberByAccount(String account) {
        return memberCoreService.findMemberByAccount(account);
    }

    public Member register(String mobileNumber) {
        Member member = new Member();
        member.setAccount(mobileNumber);
        member.setMobileNumber(mobileNumber);
        member.setPassword(bcryptEncoder.encode(mobileNumber.substring(mobileNumber.length() - 5)));
        member.setNickName("新用户：" + mobileNumber);
        memberCoreService.create(member);
        return member;
    }

    public Member findByUid(String uid) {
        return memberCoreService.findByUid(uid);
    }

}
