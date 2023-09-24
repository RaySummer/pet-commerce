package com.pet.commerce.core.module.base.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * BaseAuditJsonTreeEntity
 *
 * @author : ray
 * @since : 1.0 2023/09/18
 **/

@Getter
@Setter
@Access(AccessType.FIELD)
@MappedSuperclass
public class BaseAuditJsonTreeEntity<T extends BaseAuditEntity> extends BaseAuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    protected T parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    protected Set<T> children = new HashSet<>(0);
}
