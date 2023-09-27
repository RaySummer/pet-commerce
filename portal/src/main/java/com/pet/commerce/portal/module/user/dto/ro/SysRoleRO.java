package com.pet.commerce.portal.module.user.dto.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * SysRoleRO
 *
 * @author : ray
 * @since : 1.0 2023/09/26
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleRO implements Serializable {
    private static final long serialVersionUID = 8930522405721630014L;

    private String code;
    private String name;
    private String description;
    private Boolean status = Boolean.TRUE;
}
