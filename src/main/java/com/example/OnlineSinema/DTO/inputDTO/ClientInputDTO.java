package com.example.OnlineSinema.DTO.inputDTO;

public class ClientInputDTO {

    private String name;
    private String email;
    private String password;
    private int userId;

    public ClientInputDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public ClientInputDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
