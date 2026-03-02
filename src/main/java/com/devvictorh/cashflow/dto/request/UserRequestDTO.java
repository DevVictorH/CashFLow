package com.devvictorh.cashflow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "Campo obrigatorio")
        String name,
        @Email(message = "Email invalido")
        String email,
        @NotBlank(message = "Campo obrigatorio")
        String password,
        String role) {
}
