package com.devvictorh.cashflow.dto.response;

import java.math.BigDecimal;

public record IncomeResponseDTO(Long id, String description, BigDecimal amount, Long categoryId, String categoryName) {

	public IncomeResponseDTO(Long id, String description, BigDecimal amount) {
		this(id, description, amount, null, null);
	}
}
