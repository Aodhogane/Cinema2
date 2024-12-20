package com.example.SinemaContract.VM.form;

import com.example.SinemaContract.VM.cards.*;

import java.util.List;

public record FilmViewModel(
        BaseViewModel currentFilm,
        FilmCardVM filmInfo,
        List<ReviewCardViewModel> reviewList,
        List<ActorsCardVM> actorsList,
        List<DirectorsCardVM> directorList,
        double rating
) {
}
