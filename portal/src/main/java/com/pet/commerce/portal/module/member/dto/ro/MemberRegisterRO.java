package com.pet.commerce.portal.module.member.dto.ro;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/2/17
 */
@Getter
@Setter
public class MemberRegisterRO implements Serializable {

    @NonNull
    private String username;

    private String password;

}
