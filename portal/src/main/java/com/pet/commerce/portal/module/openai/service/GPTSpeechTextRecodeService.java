package com.pet.commerce.portal.module.openai.service;

import com.pet.commerce.core.module.member.dto.MemberDto;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.member.service.MemberCoreService;
import com.pet.commerce.core.module.openai.model.GPTSpeechTextRecode;
import com.pet.commerce.core.module.openai.service.GPTSpeechTextRecodeCoreService;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.pet.commerce.portal.module.openai.dto.vo.OpenAISpeechToTextVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Ray
 * @since 2023/3/23
 */
@Slf4j
@Service
public class GPTSpeechTextRecodeService {

    @Autowired
    private GPTSpeechTextRecodeCoreService gptSpeechTextRecodeCoreService;

    @Autowired
    private MemberCoreService memberCoreService;

    public OpenAISpeechToTextVO getSpeechToTextResult(String key) {
        Member member = memberCoreService.findByUid(WebThreadLocal.getMember().getUid());

        Assert.notNull(member, "Member is null");

        GPTSpeechTextRecode recode = gptSpeechTextRecodeCoreService.findRecode(key, member.getId());

        if (recode == null) {
            return null;
        }

        return OpenAISpeechToTextVO.builder().content(recode.getContent()).key(recode.getKey()).build();
    }

    @Transactional
    public void saveSpeechToTextRecode(MemberDto memberDto, String content, String key) {
        Assert.notNull(memberDto, "Member is null");
        Member member = memberCoreService.findByUid(memberDto.getUid());
        Assert.notNull(member, "Member is null");

        GPTSpeechTextRecode recode = new GPTSpeechTextRecode();
        recode.setMember(member);
        recode.setContent(content);
        recode.setKey(key);
        recode.setUsed(Boolean.FALSE);
        gptSpeechTextRecodeCoreService.create(recode);

    }


}
