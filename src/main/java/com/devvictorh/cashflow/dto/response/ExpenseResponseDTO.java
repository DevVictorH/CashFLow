package com.devvictorh.cashflow.dto.response;

import java.math.BigDecimal;

public record ExpenseResponseDTO(Long id, String description, BigDecimal amount, Long categoryId, String categoryName) {

	public ExpenseResponseDTO(Long id, String description, BigDecimal amount) {
		this(id, description, amount, null, null);
	}
}
