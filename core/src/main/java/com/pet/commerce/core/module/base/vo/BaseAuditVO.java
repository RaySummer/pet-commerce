package com.pet.commerce.core.module.base.vo;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @author Molly
 * @since 2022-04-21
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseAuditVO extends BaseUidVO {

    private Date createdTime;

    private Date updatedTime;


    public void setAuditInfo(BaseAuditEntity baseAuditEntity) {
        this.createdTime = baseAuditEntity.getCreatedTime();
        this.updatedTime = baseAuditEntity.getUpdatedTime();
        this.uid = baseAuditEntity.getUidStr();
    }

}
