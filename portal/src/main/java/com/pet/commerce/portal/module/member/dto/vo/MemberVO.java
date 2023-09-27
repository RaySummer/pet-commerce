package com.pet.commerce.portal.module.member.dto.vo;

import com.pet.commerce.core.module.member.model.Member;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/2/16
 */
@Getter
@Setter
public class MemberVO implements Serializable {
    private static final long serialVersionUID = 8086881951099822445L;

    private String uid;

    private String nickName;

    private Boolean gender;

    private String mobileNumber;

    private String email;

    private String account;

    private String avatar;

    private String backImage;

    private String birthday;

    private String address;

    public static MemberVO of(Member member) {
        if (ObjectUtils.isEmpty(member)) {
            return null;
        }
        MemberVO vo = new MemberVO();
        BeanUtils.copyProperties(member, vo);
        vo.setUid(member.getUidStr());
        return vo;
    }
}
