package com.pet.commerce.portal.module.member.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ray
 * @since 2023/2/16
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseVO implements Serializable {
    private static final long serialVersionUID = 5166372346156063108L;

    private String token;
    private String refreshToken;
    private Date requestDate;
}
