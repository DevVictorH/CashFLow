package com.devvictorh.cashflow.service.mapper;

import com.devvictorh.cashflow.dto.request.ExpenseRequestDTO;
import com.devvictorh.cashflow.dto.response.ExpenseResponseDTO;
import com.devvictorh.cashflow.entity.ExpenseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseEntity toEntity(ExpenseRequestDTO dto);

    ExpenseResponseDTO toResponse(ExpenseEntity entity);

    List<ExpenseResponseDTO> toResponseList(List<ExpenseEntity> list);
}
