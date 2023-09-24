package com.pet.commerce.core.module.base.vo;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 删除操作通用返回体
 *
 * @author Molly
 * @since 2022-04-21
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteVO extends BaseUidVO {

    private Boolean deleted;

    private Date deletedTime;

    public static DeleteVO of(BaseAuditEntity baseAuditEntity) {
        DeleteVO vo = new DeleteVO();
        vo.setUid(baseAuditEntity.getUidStr());
        vo.setDeleted(true);
        vo.setDeletedTime(baseAuditEntity.getDeletedTime());
        return vo;
    }

    public static DeleteVO of(String uid) {
        DeleteVO vo = new DeleteVO();
        vo.setUid(uid);
        vo.setDeleted(true);
        vo.setDeletedTime(new Date());
        return vo;
    }

    public static DeleteVO of() {
        DeleteVO vo = new DeleteVO();
        vo.setDeleted(true);
        vo.setDeletedTime(new Date());
        return vo;
    }

}
