package com.example.OnlineSinema.DTO;

import com.example.OnlineSinema.enums.UserRoles;

public class UserDTO {

    private int userId;
    private String email;
    private String password;
    private UserRoles userRoles;

    public UserDTO(String email, String password, UserRoles userRoles) {
        this.email = email;
        this.password = password;
        this.userRoles = userRoles;
    }

    public UserDTO(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }
}
