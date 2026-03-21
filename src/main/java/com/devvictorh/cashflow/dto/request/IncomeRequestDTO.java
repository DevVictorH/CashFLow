package com.devvictorh.cashflow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IncomeRequestDTO(
        @NotBlank(message = "Campo obrigatorio")
        @Schema(example = "Salario")
        String description,
        @DecimalMin(value = "1.0", message = "Valor obrigatorio")
        @Schema(example = "10000")
        BigDecimal amount,
        @NotNull(message = "Campo obrigatorio")
        @Schema(example = "1")
        Long categoryId) {
}
