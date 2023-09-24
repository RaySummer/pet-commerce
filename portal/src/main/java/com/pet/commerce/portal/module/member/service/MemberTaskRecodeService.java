package com.pet.commerce.portal.module.member.service;

import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.model.MemberTaskRecode;
import com.pet.commerce.core.module.member.model.PlatformTask;
import com.pet.commerce.core.module.member.service.MemberTaskRecodeCoreService;
import com.pet.commerce.core.module.member.service.PlatformTaskCoreService;
import com.pet.commerce.core.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @since 2023/2/16
 */

@Slf4j
@Service()
@Transactional
public class MemberTaskRecodeService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlatformTaskCoreService platformTaskCoreService;

    @Autowired
    private MemberTaskRecodeCoreService memberTaskRecodeCoreService;

    public List<MemberTaskRecode> getMemberTaskRecodes(String memberUid) {

        Member member = memberService.findByUid(memberUid);

        Assert.notNull(member, "Cannot found member");

        return memberTaskRecodeCoreService.findMemberTaskRecode(member.getId(), DateUtil.getDayBegin(), DateUtil.getDayEnd());
    }

    @Transactional
    public void completeTask(String memberUid, String taskUid) {

        Assert.notNull(memberUid, "Member uid is null");

        Member member = memberService.findByUid(memberUid);

        Assert.notNull(member, "Cannot found member");

        PlatformTask task = platformTaskCoreService.findByUid(taskUid);

        Assert.notNull(task, "Cannot found task");

        MemberTaskRecode memberTaskRecode = new MemberTaskRecode();
        memberTaskRecode.setMember(member);
        memberTaskRecode.setPlatformTask(task);
        memberTaskRecode.setCompleteTime(new Date());

        memberTaskRecodeCoreService.create(memberTaskRecode);
    }

}
