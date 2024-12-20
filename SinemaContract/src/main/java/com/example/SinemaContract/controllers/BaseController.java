package com.example.SinemaContract.controllers;


import com.example.SinemaContract.VM.cards.BaseViewModel;

public interface BaseController {
    BaseViewModel createBaseVieModel(String title, int filmId);
}
