package com.example.SinemaContract.VM.cards;

import java.util.List;

public record DirectorsCardVM(
        int directorId,
        String directorsName,
        List<String> filmName
) {
}
