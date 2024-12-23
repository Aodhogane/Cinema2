package com.example.OnlineSinema.dto.userDTO;

import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;

import java.util.List;

public class UserOutputDTO {
    private int id;
    private String name;
    private String email;
    private String password;
    private String access;
    private int accessId;


    public UserOutputDTO(int id, String name,
                         String email, String password,
                         String access) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.access = access;
    }

    public UserOutputDTO() {}

    public UserOutputDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getAccessId() {
        return accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }
}

