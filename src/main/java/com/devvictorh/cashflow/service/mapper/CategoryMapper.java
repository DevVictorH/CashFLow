package com.devvictorh.cashflow.service.mapper;

import com.devvictorh.cashflow.dto.request.CategoryRequestDTO;
import com.devvictorh.cashflow.dto.response.CategoryResponseDTO;
import com.devvictorh.cashflow.entity.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toResponse(CategoryEntity entity);

    List<CategoryResponseDTO> toResponseList(List<CategoryEntity> list);

}
