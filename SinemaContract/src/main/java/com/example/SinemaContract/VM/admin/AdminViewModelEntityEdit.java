package com.example.SinemaContract.VM.admin;

import com.example.SinemaContract.VM.cards.BaseViewModel;

public record AdminViewModelEntityEdit(
        BaseViewModel baseViewModel,
        String entityType,
        int entityId
) {
}
