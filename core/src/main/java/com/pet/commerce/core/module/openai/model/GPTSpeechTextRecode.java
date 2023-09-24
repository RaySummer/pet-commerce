package com.pet.commerce.core.module.openai.model;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import com.pet.commerce.core.module.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Ray
 * @since 2023/3/21
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "gpt_speech_text_recode", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class GPTSpeechTextRecode extends BaseAuditEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column
    private String content;

    @Column
    private String key;

    @Column
    private boolean used;
}
