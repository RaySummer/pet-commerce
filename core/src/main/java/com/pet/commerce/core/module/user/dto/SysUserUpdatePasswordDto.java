package com.pet.commerce.core.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * SysUserUpdatePasswordDto
 *
 * @author : ray
 * @since : 1.0 2023/09/26
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SysUserUpdatePasswordDto implements Serializable {
    private static final long serialVersionUID = 2002503102132835155L;

    private String account;
    private String oldPassword;
    private String newPassword;
}
