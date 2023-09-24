package com.pet.commerce.core.module.member.model;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Ray
 * @since 2023/3/26
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "member_task_recode", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class MemberTaskRecode extends BaseAuditEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column
    private Date completeTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private PlatformTask platformTask;


}
