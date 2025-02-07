package com.example.OnlineSinema.enums;

public enum Genres {
    HORROR("HORROR"), DRAMA("DRAMA"), COMEDY("COMEDY"), FANTASTIC("FANTASTIC"), TRILLER("TRILLER");

    private final String value;

    Genres(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Genres of(String value) {
        for (Genres genre : Genres.values()) {
            if (genre.getValue().equals(value.toUpperCase())) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Unknown genre: " + value);
    }
}
