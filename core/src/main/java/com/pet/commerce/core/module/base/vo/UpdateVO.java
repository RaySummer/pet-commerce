package com.pet.commerce.core.module.base.vo;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 修改操作通用返回体
 *
 * @author Molly
 * @since 2022-04-21
 */
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVO extends BaseUidVO {

    private Boolean updated;

    private Date updatedTime;

    /**
     * VO 转换方法
     *
     * @param baseAuditEntity 实体类
     * @return UpdateVO
     */
    public static UpdateVO of(BaseAuditEntity baseAuditEntity) {
        UpdateVO vo = new UpdateVO();
        vo.setUid(baseAuditEntity.getUidStr());
        vo.setUpdated(true);
        vo.setUpdatedTime(baseAuditEntity.getUpdatedTime());
        return vo;
    }

    public static UpdateVO of() {
        UpdateVO vo = new UpdateVO();
        vo.setUpdated(true);
        vo.setUpdatedTime(new Date());
        return vo;
    }

}
