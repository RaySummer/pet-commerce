package com.pet.commerce.core.module.member.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.member.model.MemberTaskRecode;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @since 2023/3/26
 */
public interface MemberTaskRecodeRepository extends BaseRepository<MemberTaskRecode, Long> {

    @Query(value = "select mtr from MemberTaskRecode mtr where mtr.member.id = :#{#memberId} and mtr.completeTime between :startTime and :endTime")
    List<MemberTaskRecode> findTodayTaskRecode(long memberId, Date startTime, Date endTime);
}
