package com.example.SinemaContract.VM.domain.review;

import java.time.LocalDateTime;

public record ReviewView (
        int id,
        String comment,
        int estimation,
        LocalDateTime dateTime,
        int filmId,
        int userId
){
}
