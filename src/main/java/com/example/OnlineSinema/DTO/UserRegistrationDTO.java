package com.example.OnlineSinema.DTO;

public class UserRegistrationDTO {

    private String email;
    private String password;
    private String confirmPassword;
    private String role;

    private String name;
    private String surname;
    private String midlName;

    public UserRegistrationDTO(String email, String password, String confirmPassword, String role, String name) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
        this.name = name;
    }

    public UserRegistrationDTO(String email, String password, String confirmPassword, String role, String name, String surname, String midlName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.midlName = midlName;
    }

    public UserRegistrationDTO(){}

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMidlName() {
        return midlName;
    }

    public void setMidlName(String midlName) {
        this.midlName = midlName;
    }
}
