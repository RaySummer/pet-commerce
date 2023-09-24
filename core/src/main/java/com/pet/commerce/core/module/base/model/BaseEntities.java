package com.pet.commerce.core.module.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.UUID;

@Getter
@Setter
@Access(AccessType.FIELD)
@Slf4j
@MappedSuperclass
public class BaseEntities {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, nullable = false, unique = true, columnDefinition = "BIGINT")
    protected Long id;

    @Column(length = 36, nullable = false, unique = true, columnDefinition = "UUID DEFAULT uuid_generate_v4()")
    protected UUID uid = UUID.randomUUID();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseEntities)) {
            return false;
        }
        BaseEntities that = (BaseEntities) o;
        return Objects.equal(getId(), that.getId())
                && Objects.equal(getUid(), that.getUid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getUid());
    }

    @JsonIgnore
    @Transient
    public String getUidStr() {
        return getUid().toString();
    }
}
