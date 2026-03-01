package com.devvictorh.cashflow.service.mapper;

import com.devvictorh.cashflow.dto.IncomeRequestDTO;
import com.devvictorh.cashflow.dto.IncomeResponseDTO;
import com.devvictorh.cashflow.entity.IncomeEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    IncomeEntity toEntity(IncomeRequestDTO dto);

    IncomeResponseDTO toResponse(IncomeEntity entity);

    List<IncomeResponseDTO> toResponseList(List<IncomeEntity> list);

}
