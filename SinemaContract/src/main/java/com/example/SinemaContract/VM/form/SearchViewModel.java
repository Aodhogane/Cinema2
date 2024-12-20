package com.example.SinemaContract.VM.form;

import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.cards.FilmCardVM;
import com.example.SinemaContract.VM.domain.film.FilmSertchForm;

import java.util.List;

public record SearchViewModel(
        BaseViewModel baseViewModel,
        FilmSertchForm filter,
        List<FilmCardVM> searchResults,
        Integer totalPage
) {
}
