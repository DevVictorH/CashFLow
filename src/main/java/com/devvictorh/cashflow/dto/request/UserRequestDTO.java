package com.devvictorh.cashflow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "Campo obrigatorio")
        @Schema(example = "Victor Hugo")
        String name,
        @Email(message = "Email invalido")
        @Schema(example = "victor@email.com")
        String email,
        @NotBlank(message = "Campo obrigatorio")
        @Schema(example = "123456")
        String password,
        @Schema(example = "ADMIN")
        String role) {
}
