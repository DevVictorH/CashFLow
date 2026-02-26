package com.devvictorh.cashflow.dto;

import com.devvictorh.cashflow.entity.enums.CategoryType;

public record CategoryRequestDTO(String name, CategoryType type) {

}
