package com.pet.commerce.core.module.openai.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.module.openai.model.ChatGPTRecode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Ray
 * @since 2023/3/2
 */
public interface ChatGPTRecodeRepository extends BaseRepository<ChatGPTRecode, Long> {

    @Query(value = "select cr from ChatGPTRecode AS cr where cr.member.id = :#{#memberId} and cr.deletedBy is null order by cr.createdTime")
    List<ChatGPTRecode> findRecodeByMember(@Param("memberId") long memberId);

    @Query(value = "delete from ChatGPTRecode cp where cp.member = :#{#member} ")
    void deleteRecodeByMember(@Param("member") Member member);

    void deleteAllByMember(Member member);
}
