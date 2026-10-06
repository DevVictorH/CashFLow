package com.devvictorh.cashflow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecoveryPasswordRequestDTO(
        @NotBlank(message = "Email é obrigatório")
        @Email
        String email
) {
}


