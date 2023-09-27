package com.pet.commerce.core.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/2/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SysRoleUpdateDto implements Serializable {

    private static final long serialVersionUID = 4686032074799450671L;

    private String code;
    private String name;
    private String description;
    private Boolean status = Boolean.TRUE;
}
