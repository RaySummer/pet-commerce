package com.pet.commerce.portal.module.openai.service;

import com.pet.commerce.core.module.member.dto.MemberDto;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.service.MemberCoreService;
import com.pet.commerce.core.module.openai.model.ChatGPTRecode;
import com.pet.commerce.core.module.openai.service.ChatGPTRecodeCoreService;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.pet.commerce.portal.module.openai.dto.vo.ChatGPTVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ray
 * @since 2023/3/2
 */
@Slf4j
@Service
public class ChatGPTRecodeService {

    @Autowired
    private ChatGPTRecodeCoreService chatGPTRecodeCoreService;

    @Autowired
    private MemberCoreService memberCoreService;

    @Transactional
    public void save(ChatGPTVO chatGPTVO) {
        Assert.notNull(chatGPTVO, "ChatGPTVO is null");

        Member member = memberCoreService.findByUid(WebThreadLocal.getMember().getUid());

        Assert.notNull(member, "Member is null");

        List<ChatGPTVO.ChatContentVO> contents = chatGPTVO.getContents();

        Assert.notEmpty(contents, "ChatContent is null");

        List<ChatGPTRecode> chatGPTRecodeList = new ArrayList<>();

        contents.forEach(chatContentVO -> {
            chatGPTRecodeList.add(ChatGPTRecode.builder()
                    .content(chatContentVO.getContent())
                    .member(member)
                    .type(chatContentVO.getType()).build());
        });
        chatGPTRecodeCoreService.deleteRecodeByMember(member);
        chatGPTRecodeCoreService.create(chatGPTRecodeList);
    }

    public ChatGPTVO findRecodeByMember(MemberDto memberDto) {

        Assert.notNull(memberDto, "Member is null");

        Member member = memberCoreService.findByUid(memberDto.getUid());
        Assert.notNull(member, "Member is null");

        return ChatGPTVO.of(ChatGPTVO.ofList(chatGPTRecodeCoreService.findRecode(member)));
    }

    @Transactional
    public void clearRecode(String memberUid) {

        Member member = memberCoreService.findByUid(memberUid);

        Assert.notNull(member, "Member is null");

        chatGPTRecodeCoreService.deleteRecodeByMember(member);
    }

}
