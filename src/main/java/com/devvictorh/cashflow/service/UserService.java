package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.request.UserRequestDTO;
import com.devvictorh.cashflow.dto.response.UserResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.exceptions.BusinessException;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public UserEntity saveUser(UserRequestDTO user) {
        if (repository.existsByEmail(user.email())){
            throw new BusinessException("Email já cadastrado");
        }
        UserEntity userEntity = mapper.toEntity(user);
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        return repository.save(userEntity);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO user) {
        var userExistente= repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        UserEntity usuario = mapper.toEntity(user);

        userExistente.setId(id);
        userExistente.setName(usuario.getName());
        userExistente.setEmail(usuario.getEmail());
        userExistente.setPassword(encoder.encode(usuario.getPassword()));

        repository.save(userExistente);
        return mapper.toResponse(userExistente);
    }

    public void deleteUser(Long id) {
        var userExistente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        repository.delete(userExistente);
    }


    public List<UserResponseDTO> findAllUsers(int page, int qtyUsers){
         Page<UserEntity> lista = repository.findAll(PageRequest.of(page, qtyUsers));
         return lista.stream().map(mapper::toResponse).toList();
    }

    public UserResponseDTO findById(Long id) {
        var userExistente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        return mapper.toResponse(userExistente);
    }
}
