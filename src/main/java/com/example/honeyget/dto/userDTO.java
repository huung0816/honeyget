package com.example.honeyget.dto;

import jakarta.persistence.Table;

@Table(name = "user_entity")
public class userDTO {

    private String userId;
    private String userPass;

    // Getter and Setter methods

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
