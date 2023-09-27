package com.pet.commerce.core.module.user.dto;

import com.pet.commerce.core.module.base.dto.BaseDto;
import com.pet.commerce.core.module.user.model.SysRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Ray
 * @since 2023/2/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SysUserDto extends BaseDto {

    private String name;
    private String email;
    private String mobileNumber;
    private String account;
    private String avatar;
    private String password;
    private List<SysRole> roleList;
}
