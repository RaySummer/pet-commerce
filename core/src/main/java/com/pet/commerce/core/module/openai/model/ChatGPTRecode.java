package com.pet.commerce.core.module.openai.model;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import com.pet.commerce.core.module.member.model.Member;
import lombok.*;

import javax.persistence.*;

/**
 * @author Ray
 * @since 2023/3/2
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "chat_gpt_recode", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class ChatGPTRecode extends BaseAuditEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column
    private String content;

    @Column
    private String type;

}
