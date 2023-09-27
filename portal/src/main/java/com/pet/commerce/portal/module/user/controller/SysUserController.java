package com.pet.commerce.portal.module.user.controller;

import com.pet.commerce.core.module.base.vo.PageVO;
import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.core.utils.Response;
import com.pet.commerce.portal.module.user.dto.ro.SysUserPageRO;
import com.pet.commerce.portal.module.user.dto.ro.SysUserRegisterRO;
import com.pet.commerce.portal.module.user.dto.ro.SysUserUpdatePasswordRO;
import com.pet.commerce.portal.module.user.dto.ro.SysUserUpdateRO;
import com.pet.commerce.portal.module.user.dto.vo.SysUserPageVO;
import com.pet.commerce.portal.module.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SysUserController
 *
 * @author : ray
 * @since : 1.0 2023/09/25
 **/
@Slf4j
@RestController
@RequestMapping({"/backstage/user"})
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/register")
    @Secured({"ROLE_SYSADMIN", "ROLE_ADMIN"})
    public Response register(@RequestBody SysUserRegisterRO ro) {
        try {
            return Response.of(sysUserService.registerUser(ro));
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @PutMapping("/modify/{account}")
    @Secured({"ROLE_SYSADMIN", "ROLE_ADMIN", "ROLE_FINANCE", "ROLE_PROCURER", "ROLE_CUSTOMER"})
    public Response modify(@PathVariable String account, @RequestBody SysUserUpdateRO ro) {
        try {
            return Response.of(sysUserService.modifyUser(account, ro));
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @DeleteMapping("/delete/{account}")
    public Response delete(@PathVariable String account) {
        try {
            sysUserService.deleteUser(account);
            return Response.of("删除成功");
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @GetMapping("/{uid}")
    public Response getUser(@PathVariable String uid) {
        try {
            return Response.of(sysUserService.getUser(uid));
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @PostMapping("/page")
    public PageVO<SysUserPageVO> searchPageByBlog(@RequestBody SysUserPageRO ro) {
        return sysUserService.searchPage(ro);
    }

    @PutMapping("/modify-password")
    public Response modifyPassword(@RequestBody SysUserUpdatePasswordRO ro) {
        try {
            sysUserService.modifyUserPassword(ro);
            return Response.of("修改成功！");
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

}
