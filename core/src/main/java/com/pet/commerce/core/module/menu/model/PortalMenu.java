package com.pet.commerce.core.module.menu.model;

import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Ray
 * @since 2023/2/20
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "portal_menu", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class PortalMenu extends BaseAuditEntity {

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String icon;

    @Column
    private boolean enable;

    @Column
    private String openType;

    @Column
    private long displayOrder;
}
