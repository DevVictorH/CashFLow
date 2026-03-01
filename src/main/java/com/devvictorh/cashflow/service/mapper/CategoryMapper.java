package com.devvictorh.cashflow.service.mapper;

import com.devvictorh.cashflow.dto.CategoryRequestDTO;
import com.devvictorh.cashflow.dto.CategoryResponseDTO;
import com.devvictorh.cashflow.entity.CategoryEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toResponse(CategoryEntity entity);

    List<CategoryResponseDTO> toResponseList(List<CategoryEntity> list);

}
