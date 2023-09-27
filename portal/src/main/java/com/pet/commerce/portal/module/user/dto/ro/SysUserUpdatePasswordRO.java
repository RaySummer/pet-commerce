package com.pet.commerce.portal.module.user.dto.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * SysUserUpdatePasswordRO
 *
 * @author : ray
 * @since : 1.0 2023/09/26
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysUserUpdatePasswordRO implements Serializable {
    private static final long serialVersionUID = -5683162120200439238L;

    @NonNull
    private String account;
    @NonNull
    private String oldPassword;
    @NonNull
    private String newPassword;
}
