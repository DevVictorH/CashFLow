package com.devvictorh.cashflow.service.mapper;

import com.devvictorh.cashflow.dto.UserRequestDTO;
import com.devvictorh.cashflow.dto.UserResponseDTO;
import com.devvictorh.cashflow.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(User user);

}
