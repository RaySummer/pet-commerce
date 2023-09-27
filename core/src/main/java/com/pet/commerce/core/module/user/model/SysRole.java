package com.pet.commerce.core.module.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;

/**
 * SysRole
 *
 * @author : ray
 * @since : 1.0 2023/09/26
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "sys_role", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class SysRole extends BaseAuditEntity {

    private String code;

    private String name;

    private String description;

    private boolean status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sysRole", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SysUserRSysRole> sysUserRSysRoles = new HashSet<>();

}
