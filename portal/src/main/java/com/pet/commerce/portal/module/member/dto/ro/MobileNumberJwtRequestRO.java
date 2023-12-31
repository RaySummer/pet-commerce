package com.pet.commerce.portal.module.member.dto.ro;

import java.io.Serializable;

public class MobileNumberJwtRequestRO implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;

    //need default constructor for JSON Parsing
    public MobileNumberJwtRequestRO() {

    }

    public MobileNumberJwtRequestRO(String username) {
        this.setUsername(username);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
