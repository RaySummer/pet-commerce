package com.pet.commerce.core.module.base.vo;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 创建操作通用返回体
 *
 * @author Molly
 * @since 2022-04-21
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreateVO extends BaseUidVO {

    private Boolean created;

    private Date createdTime;

    /**
     * VO 转换方法
     *
     * @param baseAuditEntity 实体类
     * @return CreateVO
     */
    public static CreateVO of(BaseAuditEntity baseAuditEntity) {
        CreateVO vo = new CreateVO();
        vo.setUid(baseAuditEntity.getUidStr());
        vo.setCreated(true);
        vo.setCreatedTime(baseAuditEntity.getCreatedTime());
        return vo;
    }

}
