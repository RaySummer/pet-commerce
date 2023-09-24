package com.pet.commerce.portal.module.menu.controller;

import com.pet.commerce.core.utils.Response;
import com.pet.commerce.portal.module.menu.service.PortalMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ray
 * @since 2023/2/20
 */
@Slf4j
@RestController
@RequestMapping({"/menu"})
public class PortalMenuController {

    @Autowired
    private PortalMenuService portalMenuService;

    @GetMapping
    public Response findMenu() {
        return Response.of(portalMenuService.findMenu());
    }

}
