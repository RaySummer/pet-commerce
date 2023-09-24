package com.pet.commerce.portal.module.member.dto.ro;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ray
 * @since 2023/2/17
 */
@Getter
@Setter
public class PlatformTaskRO implements Serializable {

    @NonNull
    private String taskName;

    @NonNull
    private Date expireTime;

    private Long displayOrder;
}
