package com.example.SinemaContract.VM.form;

import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.cards.FilmCardVM;
import com.example.SinemaContract.VM.cards.ReviewCardViewModel;
import com.example.SinemaContract.VM.cards.UserCardVM;

import java.util.List;

public record UserProfileViewModel(
        BaseViewModel currentUser,
        UserCardVM userCard,
        List<ReviewCardViewModel> reviewList,
        List<FilmCardVM> listFilm
) {
}
