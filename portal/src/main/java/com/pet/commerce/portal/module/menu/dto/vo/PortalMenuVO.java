package com.pet.commerce.portal.module.menu.dto.vo;

import com.pet.commerce.core.module.menu.model.PortalMenu;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ray
 * @since 2023/2/20
 */
@Getter
@Setter
public class PortalMenuVO implements Serializable {

    private static final long serialVersionUID = -3918678751372589059L;

    private String name;

    private String description;

    private String icon;

    private String openType;

    public static PortalMenuVO of(PortalMenu portalMenu) {
        if (portalMenu == null) {
            return null;
        }
        PortalMenuVO vo = new PortalMenuVO();
        BeanUtils.copyProperties(portalMenu, vo);
        return vo;
    }

    public static List<PortalMenuVO> ofList(List<PortalMenu> portalMenuList) {
        if (portalMenuList.isEmpty()) {
            return null;
        }
        return portalMenuList.stream().map(PortalMenuVO::of).collect(Collectors.toList());
    }
}
