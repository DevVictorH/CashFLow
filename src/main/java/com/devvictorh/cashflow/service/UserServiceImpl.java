package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.UserRequestDTO;
import com.devvictorh.cashflow.dto.UserResponseDTO;
import com.devvictorh.cashflow.entity.User;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public User salvar(UserRequestDTO dto) {
        User user = mapper.toEntity(dto);
        return repository.save(user);
    }

    @Override
    public User atualizar(Long id, UserRequestDTO user) {
        var userExistente= repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        User usuario = mapper.toEntity(user);

        userExistente.setId(id);
        userExistente.setName(usuario.getName());
        userExistente.setEmail(usuario.getEmail());
        userExistente.setPassword(usuario.getPassword());

        return repository.save(userExistente);
    }

    @Override
    public void deletar(Long id) {
        var userExistente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        repository.deleteById(id);
    }

    @Override
    public List<UserResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public UserResponseDTO buscarPorId(Long id) {
        var userExistente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        return mapper.toResponse(userExistente);
    }
}
