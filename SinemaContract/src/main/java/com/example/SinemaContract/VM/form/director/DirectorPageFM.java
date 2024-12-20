package com.example.SinemaContract.VM.form.director;

import jakarta.validation.constraints.Min;

public record DirectorPageFM(
        @Min(value = 0, message = "Page cannot be less than 0")
        Integer clientPage,
        @Min(value = 1, message = "Page size must be greater than 0")
        Integer clientSize
) {
}
