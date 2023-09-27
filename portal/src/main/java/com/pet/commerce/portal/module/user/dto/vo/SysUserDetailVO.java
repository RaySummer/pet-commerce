package com.pet.commerce.portal.module.user.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
public class SysUserDetailVO implements Serializable {
    private static final long serialVersionUID = 5401100812083125147L;

    private String uid;
    private String account;
    private String name;
    private String email;
    private String mobileNumber;
    private String avatar;
    private List<SysRoleDetailVO> sysRoleList = new ArrayList<>();
}
