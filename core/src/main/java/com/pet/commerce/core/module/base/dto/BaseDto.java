package com.pet.commerce.core.module.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ray
 * @since 2023/2/15
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class BaseDto implements Serializable {

    protected Long id;

    protected UUID uid;

    protected Date createdTime;

    protected String createdBy;

    protected Date updatedTime;

    protected String updatedBy;

    protected Date deletedTime;

    protected String deletedBy;

    @JsonIgnore
    public String getUidStr() {
        return uid.toString();
    }

}
