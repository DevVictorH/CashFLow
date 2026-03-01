package com.devvictorh.cashflow.service.mapper;

import com.devvictorh.cashflow.dto.IncomeRequestDTO;
import com.devvictorh.cashflow.entity.IncomeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    IncomeEntity toEntity(IncomeRequestDTO dto);

}
