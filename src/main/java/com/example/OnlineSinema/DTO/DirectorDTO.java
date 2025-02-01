package com.example.OnlineSinema.DTO;

public class DirectorDTO {
    private int id;
    private String name;
    private String surname;
    private String midlName;
    private int userId;

    public DirectorDTO(String name, String surname, String midlName) {
        this.name = name;
        this.surname = surname;
        this.midlName = midlName;
    }

    public DirectorDTO(){}

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
