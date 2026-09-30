package com.devvictorh.cashflow.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseResponseDTO(Long id, String description, BigDecimal amount, Long categoryId, String categoryName, LocalDateTime createdAt) {

	public ExpenseResponseDTO(Long id, String description, BigDecimal amount) {
		this(id, description, amount, null, null, null);
	}
}
