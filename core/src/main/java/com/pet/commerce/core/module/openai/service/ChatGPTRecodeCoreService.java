package com.pet.commerce.core.module.openai.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.openai.model.ChatGPTRecode;
import com.pet.commerce.core.module.openai.repository.ChatGPTRecodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ray
 * @since 2023/3/2
 */
@Slf4j
@Service
@Transactional
public class ChatGPTRecodeCoreService extends BaseCrudServiceImpl<ChatGPTRecodeRepository, ChatGPTRecode, Long> {

    public List<ChatGPTRecode> findRecode(Member member) {
        return baseRepository.findRecodeByMember(member.getId());
    }

    @Transactional
    public void saveOrUpdateRecode(List<ChatGPTRecode> chatGPTRecodeList) {
        baseRepository.saveAllAndFlush(chatGPTRecodeList);
    }

    public void deleteRecodeByMember(Member member) {
        baseRepository.deleteAllByMember(member);
        baseRepository.flush();
    }
}
