package com.pet.commerce.portal.module.member.dto.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/2/17
 */
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterRO implements Serializable {

    @NonNull
    private String username;

    private String password;

}
