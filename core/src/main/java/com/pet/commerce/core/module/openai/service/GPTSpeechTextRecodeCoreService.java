package com.pet.commerce.core.module.openai.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.openai.model.GPTSpeechTextRecode;
import com.pet.commerce.core.module.openai.repository.GPTSpeechTextRecodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @since 2023/3/2
 */
@Slf4j
@Service
public class GPTSpeechTextRecodeCoreService extends BaseCrudServiceImpl<GPTSpeechTextRecodeRepository, GPTSpeechTextRecode, Long> {

    public GPTSpeechTextRecode findRecode(String key, long memberId) {
        return baseRepository.findRecode(key, memberId);
    }
}
