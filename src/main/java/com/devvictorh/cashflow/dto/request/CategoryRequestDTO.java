package com.devvictorh.cashflow.dto.request;

import com.devvictorh.cashflow.entity.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(
        @NotBlank(message = "Campo obrigatorio")
        @Schema(example = "Alimentação")
        String name,
        @NotNull(message = "Campo obrigatorio")
        @Schema(example = "EXPENSE")
        CategoryType type) {

}
