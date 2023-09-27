package com.pet.commerce.portal.module.user.dto.vo;

import com.pet.commerce.core.module.base.vo.BaseAuditVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SysUserDetailVO
 *
 * @author : ray
 * @since : 1.0 2023/09/26
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysUserPageVO extends BaseAuditVO {
    private static final long serialVersionUID = 6627303778876012269L;

    private String name;
    private String email;
    private String mobileNumber;
    private String avatar;
    private List<SysRoleDetailVO> sysRoleList = new ArrayList<>();
}
