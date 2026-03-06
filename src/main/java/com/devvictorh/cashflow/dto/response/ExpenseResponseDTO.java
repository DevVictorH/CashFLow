package com.devvictorh.cashflow.dto.response;

import java.math.BigDecimal;

public record ExpenseResponseDTO(Long id, String description, BigDecimal amount) {
}
