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

    public static UserRoles of(String value) {
        for (UserRoles userRoles : UserRoles.values()) {
            if (userRoles.getValue().equals(value.toUpperCase())) {
                return userRoles;
            }
        }
        throw new IllegalArgumentException("Unknown user roles: " + value);
    }
}
