package com.example.OnlineSinema.dto.userDTO;

import java.util.List;

public class UserInfoDTO {
    private int id;
    private String name;
    private List<String> reviews;

    public UserInfoDTO(int id, String name, List<String> reviews) {
        this.id = id;
        this.name = name;
        this.reviews = reviews;
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

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }
}
