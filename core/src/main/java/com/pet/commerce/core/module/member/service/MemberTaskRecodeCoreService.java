package com.pet.commerce.core.module.member.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.member.model.MemberTaskRecode;
import com.pet.commerce.core.module.member.repository.MemberTaskRecodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @since 2023/3/26
 */
@Slf4j
@Service
@Transactional
public class MemberTaskRecodeCoreService extends BaseCrudServiceImpl<MemberTaskRecodeRepository, MemberTaskRecode, Long> {

    public List<MemberTaskRecode> findMemberTaskRecode(long memberId, Date startTime, Date endTime) {
        return baseRepository.findTodayTaskRecode(memberId, startTime, endTime);
    }
}
