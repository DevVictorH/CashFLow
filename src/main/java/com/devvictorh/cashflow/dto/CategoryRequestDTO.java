package com.devvictorh.cashflow.dto;

import com.devvictorh.cashflow.entity.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(
        @NotBlank(message = "Campo obrigatorio")
        String name,
        @NotNull(message = "Campo obrigatorio")
        CategoryType type) {

}
