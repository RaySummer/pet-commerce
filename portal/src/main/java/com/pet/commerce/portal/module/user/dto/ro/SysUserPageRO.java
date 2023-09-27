package com.pet.commerce.portal.module.user.dto.ro;

import com.pet.commerce.core.module.base.ro.PageRO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ray
 * @since 2023/7/11
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysUserPageRO extends PageRO {
    private static final long serialVersionUID = -5229762037576361735L;

    private String keyword;
    private String roleCode;

}
