package com.example.SinemaContract.VM.form.review;

import jakarta.validation.constraints.Min;

public record ReviewPageFormModel(
        @Min(value = 0, message = "Page cannot be less than 0")
        Integer reviewPage,

        @Min(value = 1, message = "Page size must be greater than 0")
        Integer reviewSize
) {
}
