package com.pet.commerce.portal.module.member.controller;

import com.pet.commerce.core.utils.Response;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.pet.commerce.portal.module.member.dto.ro.PlatformTaskRO;
import com.pet.commerce.portal.module.member.service.PlatformTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ray
 * @since 2023/3/26
 */
@Slf4j
@RestController
@RequestMapping({"/platform-task"})
public class PlatformTaskController {

    @Autowired
    private PlatformTaskService platformTaskService;

    @GetMapping()
    public Response findTasks() {
        return Response.of(platformTaskService.findTask(WebThreadLocal.getMember().getUidStr()));
    }

    @PostMapping("/create")
    public Response createTask(@RequestBody PlatformTaskRO ro) {
        try {
            platformTaskService.saveTask(ro);
            return Response.of();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ofError(500, e.getMessage());
        }
    }

}
