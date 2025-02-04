package com.example.OnlineSinema.DTO.inputDTO;

public class DirectorInputDTO {

    private String name;
    private String surname;
    private String midlName;
    private String email;
    private String password;

    public DirectorInputDTO(String name, String surname, String midlName, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.midlName = midlName;
        this.email = email;
        this.password = password;
    }

    public DirectorInputDTO(){}

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
