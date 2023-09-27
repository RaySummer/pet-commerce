package com.pet.commerce.portal.module.user.controller;

import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.core.utils.Response;
import com.pet.commerce.portal.module.user.dto.ro.SysRoleRO;
import com.pet.commerce.portal.module.user.dto.vo.SysRoleDetailVO;
import com.pet.commerce.portal.module.user.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SysRoleController
 *
 * @author : ray
 * @since : 1.0 2023/09/26
 **/
@Slf4j
@RestController
@RequestMapping({"/backstage/role"})
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/get/{code}")
    public Response findOneByCode(@PathVariable String code) {
        try {
            return Response.of(sysRoleService.findOneByCode(code));
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @GetMapping("/find")
    public List<SysRoleDetailVO> findALlRoles() {
        return sysRoleService.findAllRoles();
    }

    @PostMapping("/create")
    public Response createRole(@RequestBody SysRoleRO ro) {
        try {
            return Response.of(sysRoleService.createRole(ro));
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @PutMapping("/modify/{uid}")
    public Response updateRole(@PathVariable String uid, @RequestBody SysRoleRO ro) {
        try {
            return Response.of(sysRoleService.modifyRole(uid, ro));
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

    @DeleteMapping("/delete/{uid}")
    public Response deleteRole(@PathVariable String uid) {
        try {
            sysRoleService.deleteRole(uid);
            return Response.of("删除成功");
        } catch (CustomizeException e) {
            log.error(e.getMsg());
            return Response.ofError(500, e.getMsg());
        }
    }

}
