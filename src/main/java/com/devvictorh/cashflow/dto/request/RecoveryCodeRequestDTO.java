package com.devvictorh.cashflow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecoveryCodeRequestDTO(
        @NotBlank(message = "Email é obrigatório")
        @Email
        String email,
        @NotBlank(message = "Código é obrigatório")
        String code
) {
}