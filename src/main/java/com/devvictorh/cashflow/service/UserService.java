package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.UserRequestDTO;
import com.devvictorh.cashflow.dto.UserResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserEntity saveUser(UserRequestDTO dto) {
        UserEntity userEntity = mapper.toEntity(dto);
        return repository.save(userEntity);
    }

    public UserEntity updateUser(Long id, UserRequestDTO user) {
        var userExistente= repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        UserEntity usuario = mapper.toEntity(user);

        userExistente.setId(id);
        userExistente.setName(usuario.getName());
        userExistente.setEmail(usuario.getEmail());
        userExistente.setPassword(usuario.getPassword());

        return repository.save(userExistente);
    }

    public void deleteUser(Long id) {
        var userExistente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        repository.delete(userExistente);
    }

    public List<UserResponseDTO> listAllUsers() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public UserResponseDTO findById(Long id) {
        var userExistente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        return mapper.toResponse(userExistente);
    }
}
