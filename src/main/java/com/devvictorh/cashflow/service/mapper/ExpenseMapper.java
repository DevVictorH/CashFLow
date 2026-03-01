package com.devvictorh.cashflow.service.mapper;

import com.devvictorh.cashflow.dto.ExpenseRequestDTO;
import com.devvictorh.cashflow.entity.ExpenseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseEntity toEntity(ExpenseRequestDTO dto);
}
