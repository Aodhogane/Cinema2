package com.example.OnlineSinema.DTO;

public class ClientDTO {

    private int id;
    private String name;
    private int userId;

    public ClientDTO(String name) {
        this.name = name;
    }

    public ClientDTO(){}

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
