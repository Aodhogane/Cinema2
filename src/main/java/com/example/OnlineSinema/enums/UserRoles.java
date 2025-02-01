package com.example.OnlineSinema.enums;

public enum UserRoles {
    CLIENT("CLIENT"), ACTOR("ACTOR"), DIRECTOR("DIRECTOR"), ADMIN("ADMIN");

    private final String value;

    UserRoles(String client) {
        this.value = client;
    }

    public String getValue() {
        return value;
    }
}
