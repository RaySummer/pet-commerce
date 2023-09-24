package com.pet.commerce.portal.module.member.controller;

import com.pet.commerce.core.annotation.NoRepeatSubmit;
import com.pet.commerce.core.module.member.dto.MemberDto;
import com.pet.commerce.core.utils.Response;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.pet.commerce.portal.module.member.dto.ro.MemberRO;
import com.pet.commerce.portal.module.member.dto.ro.MemberRegisterRO;
import com.pet.commerce.portal.module.member.service.MemberService;
import com.pet.commerce.portal.module.member.service.MemberTaskRecodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ray
 * @since 2023/2/16
 */
@Slf4j
@RestController
@RequestMapping({"/member"})
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberTaskRecodeService memberTaskRecodeService;

    @PostMapping("/register")
    public Response register(@RequestBody MemberRegisterRO ro) {
        return Response.of(memberService.register(ro));
    }

    @PostMapping("/quickly-register")
    public Response quicklyRegister(@RequestBody MemberRegisterRO ro) {
        return Response.of(memberService.register(ro));
    }

    @PostMapping("/modify")
    public Response modify(@RequestBody MemberRO ro) {
        return Response.of(memberService.modify(ro));
    }

    @GetMapping("/member-info")
    public Response getMemberInfo() {
        return Response.of(WebThreadLocal.getMember());
    }

    @NoRepeatSubmit
    @PostMapping("/complete-task/{taskUid}")
    public Response completeTask(@PathVariable String taskUid) {
        try {
            memberTaskRecodeService.completeTask(WebThreadLocal.getMember().getUidStr(), taskUid);
            return Response.of();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ofError(500, e.getMessage());
        }
    }
}
