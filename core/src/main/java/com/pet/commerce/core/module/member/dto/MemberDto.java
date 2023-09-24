package com.pet.commerce.core.module.member.dto;

import com.pet.commerce.core.module.base.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ray
 * @since 2023/2/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDto extends BaseDto {

    private String nickName;
    private String gender;
    private String mobileNumber;
    private String email;
    private String account;
    private String avatar;
    private String password;
    private String backImage;
    private String birthday;
    private String address;

}
