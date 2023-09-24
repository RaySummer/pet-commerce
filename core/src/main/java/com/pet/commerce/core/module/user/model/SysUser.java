package com.pet.commerce.core.module.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import com.pet.commerce.core.module.media.model.Media;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ray
 * @since 2023/2/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "sys_user", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class SysUser extends BaseAuditEntity {

    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String mobileNumber;
    @Column
    private String account;
    @Column
    private String avatar;
    @Column
    private String password;
    @Column
    private String roleId;
    @Column
    private String adCode;

    @Column
    private double longitude;

    @Column
    private double latitude;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sysUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Media> mediaSet = new HashSet<>();

}
