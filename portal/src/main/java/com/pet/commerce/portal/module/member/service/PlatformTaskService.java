package com.pet.commerce.portal.module.member.service;

import com.pet.commerce.core.module.member.model.MemberTaskRecode;
import com.pet.commerce.core.module.member.model.PlatformTask;
import com.pet.commerce.core.module.member.service.PlatformTaskCoreService;
import com.pet.commerce.portal.module.member.dto.ro.PlatformTaskRO;
import com.pet.commerce.portal.module.member.dto.vo.PlatformTaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ray
 * @since 2023/2/16
 */

@Slf4j
@Service()
@Transactional
public class PlatformTaskService {

    @Autowired
    private PlatformTaskCoreService platformTaskCoreService;

    @Autowired
    private MemberTaskRecodeService memberTaskRecodeService;

    public List<PlatformTaskVO> findTask(String memberUid) {
        List<PlatformTaskVO> platformTaskVOS = PlatformTaskVO.ofList(platformTaskCoreService.findTask());
        List<MemberTaskRecode> memberTaskRecodes = memberTaskRecodeService.getMemberTaskRecodes(memberUid);
        for (PlatformTaskVO platformTaskVO : platformTaskVOS) {
            for (MemberTaskRecode memberTaskRecode : memberTaskRecodes) {
                if (platformTaskVO.getTaskUid().equals(memberTaskRecode.getPlatformTask().getUidStr())) {
                    platformTaskVO.setMemberIsComplete(Boolean.TRUE);
                }
            }
        }
        return platformTaskVOS;
    }

    public PlatformTask findByUid(String uid) {
        return platformTaskCoreService.findByUid(uid);
    }

    @Transactional
    public void saveTask(PlatformTaskRO ro) {
        PlatformTask platformTask = new PlatformTask();
        platformTask.setTaskName(ro.getTaskName());
        platformTask.setExpireTime(ro.getExpireTime());
        platformTask.setDisplayOrder(ro.getDisplayOrder());
        platformTaskCoreService.create(platformTask);
    }

}
