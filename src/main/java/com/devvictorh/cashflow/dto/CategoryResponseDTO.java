package com.devvictorh.cashflow.dto;

import com.devvictorh.cashflow.entity.enums.CategoryType;

public record CategoryResponseDTO(Long id, String name, CategoryType type) {
}
