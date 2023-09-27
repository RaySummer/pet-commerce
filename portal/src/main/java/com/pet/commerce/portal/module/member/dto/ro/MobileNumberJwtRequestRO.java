package com.pet.commerce.portal.module.member.dto.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobileNumberJwtRequestRO implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;

}
