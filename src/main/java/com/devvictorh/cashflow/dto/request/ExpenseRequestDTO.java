package com.devvictorh.cashflow.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExpenseRequestDTO(
        @NotBlank(message = "Campo obrigatorio")
        String description,
        @Min(value = 1, message = "Valor obrigatorio")
        BigDecimal amount,
        @NotNull(message = "Campo obrigatorio")
        Long categoryId) {
}
