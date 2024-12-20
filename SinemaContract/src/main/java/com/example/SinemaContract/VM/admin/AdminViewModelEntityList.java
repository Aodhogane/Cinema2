package com.example.SinemaContract.VM.admin;

import com.example.SinemaContract.VM.cards.BaseViewModel;

import java.util.List;

public record AdminViewModelEntityList<T, P>(
        BaseViewModel baseViewModel,
        List<T> currentList,
        P pagination,
        String entityType,
        int totalPages
) {
}
