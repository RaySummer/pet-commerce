package com.pet.commerce.portal.module.member.dto.ro;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/2/16
 */
@Getter
@Setter
public class MemberRO implements Serializable {
    private static final long serialVersionUID = 8086881951099822445L;

    private String nickName;

    private Boolean gender;

    private String mobileNumber;

    private String email;

    private String account;

    private String avatar;

    private String backImage;

    private String birthday;

    private String address;


}
