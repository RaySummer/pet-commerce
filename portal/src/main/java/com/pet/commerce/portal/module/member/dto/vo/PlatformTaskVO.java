package com.pet.commerce.portal.module.member.dto.vo;

import com.pet.commerce.core.module.member.model.PlatformTask;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ray
 * @since 2023/3/26
 */
@Getter
@Setter
public class PlatformTaskVO implements Serializable {
    private static final long serialVersionUID = 8086881951099822445L;

    private String taskUid;
    private String taskName;
    private Date expireTime;
    private Long displayOrder;
    private Boolean memberIsComplete = Boolean.FALSE;

    public static PlatformTaskVO of(PlatformTask task) {
        PlatformTaskVO vo = new PlatformTaskVO();
        vo.setTaskUid(task.getUidStr());
        vo.setTaskName(task.getTaskName());
        vo.setExpireTime(task.getExpireTime());
        vo.setDisplayOrder(task.getDisplayOrder());
        return vo;
    }

    public static List<PlatformTaskVO> ofList(List<PlatformTask> tasks) {
        return tasks.stream().map(PlatformTaskVO::of).collect(Collectors.toList());
    }

}
