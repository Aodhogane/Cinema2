package com.example.SinemaContract.VM.cards;

import java.time.LocalDateTime;

public record ReviewCardViewModel(
        int reviewId,
        String userName,
        int userId,
        String filmMane,
        int filmId,
        int rating,
        String text,
        LocalDateTime dateTime
) {
}
