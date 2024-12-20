package com.example.SinemaContract.VM.form;

import com.example.SinemaContract.VM.cards.BaseViewModel;

public record ReviewViewModel(
        BaseViewModel baseViewModel,
        int reviewId,
        String filmName,
        int filmId,
        String userName,
        int userId,
        String text,
        int rating
) {
}
