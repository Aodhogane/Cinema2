package com.example.SinemaContract.VM.form.actor;

import jakarta.validation.constraints.Min;

public record ActorPageFM (
        @Min(value = 0, message = "Page cannot be less than 0")
        Integer clientPage,
        @Min(value = 1, message = "Page size must be greater than 0")
        Integer clientSize
){
}
