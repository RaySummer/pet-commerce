package com.pet.commerce.portal.module.user.dto.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * SysUserUpdateRO
 *
 * @author : ray
 * @since : 1.0 2023/09/25
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysUserUpdateRO implements Serializable {
    private static final long serialVersionUID = 3643518967334136088L;

    private String name;
    private String email;
    private String mobileNumber;
    private String avatar;
    private List<String> roleCodes;

}
