package com.pet.commerce.core.module.openai.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.openai.model.GPTSpeechTextRecode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Ray
 * @since 2023/3/2
 */
public interface GPTSpeechTextRecodeRepository extends BaseRepository<GPTSpeechTextRecode, Long> {

    @Query(value = "select re from GPTSpeechTextRecode re where re.key=:#{#key} and re.member.id = :#{#memberId} ")
    GPTSpeechTextRecode findRecode(@Param("key") String key, @Param("memberId") long memberId);

}
