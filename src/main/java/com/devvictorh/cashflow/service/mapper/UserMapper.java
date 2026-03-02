package com.devvictorh.cashflow.service.mapper;

import com.devvictorh.cashflow.dto.request.UserRequestDTO;
import com.devvictorh.cashflow.dto.response.UserResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserEntity toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(UserEntity userEntity);

}
