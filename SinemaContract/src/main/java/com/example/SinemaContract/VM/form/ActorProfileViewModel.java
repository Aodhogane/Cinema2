package com.example.SinemaContract.VM.form;


import com.example.SinemaContract.VM.cards.ActorsCardVM;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.cards.FilmCardVM;

import java.util.List;

public record ActorProfileViewModel(
        BaseViewModel currentActor,
        ActorsCardVM actorCard,
        List<FilmCardVM> listFilm
) {
}
