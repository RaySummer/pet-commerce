package com.pet.commerce.core.module.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Molly
 * @since 2023-06-21
 */
@Getter
@Setter
@Access(AccessType.FIELD)
@MappedSuperclass
public class BaseAuditEntity extends BaseEntities {

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp with time zone")
    protected Date createdTime;

    @Column(nullable = false, updatable = false)
    protected String createdBy;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    protected Date updatedTime;

    @Column
    protected String updatedBy;

    @Column(columnDefinition = "timestamp with time zone")
    protected Date deletedTime;

    @Column
    protected String deletedBy;

    @JsonIgnore
    @Transient
    public boolean isDeleted() {
        return deletedTime != null;
    }

}
