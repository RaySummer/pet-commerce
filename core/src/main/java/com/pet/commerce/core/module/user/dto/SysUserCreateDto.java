package com.pet.commerce.core.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ray
 * @since 2023/2/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SysUserCreateDto implements Serializable {

    private static final long serialVersionUID = 4686032074799450671L;

    private String name;
    private String email;
    private String mobileNumber;
    private String account;
    private String avatar;
    private String password;
    private List<String> roleCodes = new ArrayList<>();
}
