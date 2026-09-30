package com.devvictorh.cashflow.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IncomeResponseDTO(Long id, String description, BigDecimal amount, Long categoryId, String categoryName, LocalDateTime createdAt) {

	public IncomeResponseDTO(Long id, String description, BigDecimal amount) {
		this(id, description, amount, null, null, null);
	}
}
