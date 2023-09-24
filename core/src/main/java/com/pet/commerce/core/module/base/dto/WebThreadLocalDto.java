package com.pet.commerce.core.module.base.dto;

import com.pet.commerce.core.module.member.dto.MemberDto;
import com.pet.commerce.core.module.user.dto.SysUserDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ray
 * @since 2023/02/16
 */
@Getter
@Setter
public class WebThreadLocalDto implements Serializable {
    private static final long serialVersionUID = -1216792075358870797L;

    private SysUserDto user;
    private String hostname;
    private Date time;
    private String browserFingerprint;
    private MemberDto memberDto;
}
