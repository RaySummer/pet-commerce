package com.pet.commerce.portal.module.menu.service;

import com.pet.commerce.core.module.menu.service.PortalMenuCoreService;
import com.pet.commerce.portal.cache.CacheDataUtil;
import com.pet.commerce.portal.module.menu.dto.vo.PortalMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ray
 * @since 2023/2/20
 */
@Service
public class PortalMenuService {

    @Autowired
    private PortalMenuCoreService portalMenuCoreService;

    public List<PortalMenuVO> findMenu() {

        List<PortalMenuVO> voList = CacheDataUtil.portalMenuVOList;

        if (voList == null || voList.isEmpty()) {
            voList = PortalMenuVO.ofList(portalMenuCoreService.findMenu());
        }

        return voList;
    }
}
