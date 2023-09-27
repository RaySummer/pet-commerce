package com.pet.commerce.portal.module.user.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * SysRoleDetailVO
 *
 * @author : ray
 * @since : 1.0 2023/09/26
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleDetailVO implements Serializable {
    private static final long serialVersionUID = 3598826117197184363L;

    private String code;

    private String name;

    private String description;

    private Boolean status;
}
