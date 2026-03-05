package com.devvictorh.cashflow.dto.response;

import java.math.BigDecimal;

public record IncomeResponseDTO(Long id, String description, BigDecimal amount) {
}
