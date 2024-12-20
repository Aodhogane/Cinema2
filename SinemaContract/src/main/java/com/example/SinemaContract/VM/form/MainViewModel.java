package com.example.SinemaContract.VM.form;

import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.cards.FilmCardVM;

import java.util.List;

public record MainViewModel(
        BaseViewModel base,
        List<FilmCardVM> film
) {
}
