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
@Table(name = "platform_task", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class PlatformTask extends BaseAuditEntity {

    @Column
    private String taskName;

    @Column
    private Date expireTime;

    @Column
    private long displayOrder;

}
