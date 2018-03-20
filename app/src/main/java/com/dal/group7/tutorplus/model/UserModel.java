package com.dal.group7.tutorplus.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String userId;
    private String name;
    private String role;

    public UserModel(){

    }
    public UserModel(String userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
