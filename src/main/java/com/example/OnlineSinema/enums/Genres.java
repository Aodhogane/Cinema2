package com.example.OnlineSinema.enums;

public enum Genres {
    HORROR("HOROR"), DRAMA("DRAMA"), COMEDY("COMEDY"), FANTASTIC("FANTASTIC"), TRILLER("TRILLER");

    private final String value;

    Genres(String client) {
        this.value = client;
    }

    public String getValue() {
        return value;
    }
}
