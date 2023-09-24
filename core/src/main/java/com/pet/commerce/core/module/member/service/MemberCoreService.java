package com.pet.commerce.core.module.member.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.model.QMember;
import com.pet.commerce.core.module.member.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ray
 * @since 2023/2/15
 */
@Slf4j
@Service
@Transactional
public class MemberCoreService extends BaseCrudServiceImpl<MemberRepository, Member, Long> {

    public Member findMemberByAccount(String account) {
        return baseRepository.findByAccount(account);
    }

    public Member findPlatformMember(String account, String mobileNumber) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QMember.member.account.eq(account)).and(QMember.member.mobileNumber.eq(mobileNumber));
        return findOne(builder);
    }
}
