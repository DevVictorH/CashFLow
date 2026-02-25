package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.UserRequestDTO;
import com.devvictorh.cashflow.dto.UserResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;

import java.util.List;

public interface UserService {

    public UserEntity salvar(UserRequestDTO user);

    public UserEntity atualizar(Long id, UserRequestDTO user);

    public void deletar(Long id);

    public List<UserResponseDTO> listar();

    public UserResponseDTO buscarPorId(Long id);

}
