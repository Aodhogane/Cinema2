package com.example.SinemaContract.VM.cards;

import java.util.List;

public record ActorsCardVM(
        int actorId,
        String actorsName,
        List<String> nameFilms
) {
}
