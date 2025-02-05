package com.example.OnlineSinema.DTO;

public class BaseUserDTO {

    private int id;
    private String name;

    public BaseUserDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseUserDTO(){}

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
}
