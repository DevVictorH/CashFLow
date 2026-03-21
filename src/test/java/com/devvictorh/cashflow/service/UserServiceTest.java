package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.request.UserRequestDTO;
import com.devvictorh.cashflow.dto.response.UserResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserRequestDTO user;
    UserResponseDTO userResponseDTO;

    UserEntity entity;

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder encoder;

    @Mock
    UserMapper mapper;

    @BeforeEach
    void setUp() {

        user = new UserRequestDTO("Victor", "victor@gmail.com", "123", "USER");

        userResponseDTO = new UserResponseDTO(1L, "Victor", "victor@gmail.com");

        entity = new UserEntity();
        entity.setName("Victor");
        entity.setEmail("victor@gmail.com");
        entity.setPassword("123");
    }

    @Test
    void shouldSaveUser() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(entity);
        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(entity);
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn("senhaCriptografada");


        var savedUser = userService.saveUser(user);

        Assertions.assertNotNull(savedUser);
        Mockito.verify(encoder).encode("123");
        Mockito.verify(userRepository).save(Mockito.any());
    }

    @Test
    void shouldUpdateUser() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(entity);
        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(entity);
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn("senhaCriptografada");
        Mockito.when(mapper.toResponse(Mockito.any())).thenReturn(userResponseDTO);

        var savedUser = userService.updateUser(1L, user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("Victor",userResponseDTO.name());
        Mockito.verify(userRepository).save(Mockito.any());
    }

    @Test
    void shouldThrowErrorWhenUpdateUser(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.updateUser(1l, user));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldDeleteUser() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        userService.deleteUser(1L);

        Mockito.verify(userRepository, Mockito.times(1)).delete(entity);

    }

    @Test
    void shouldThrowErrorWhenDeleteUser(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));
        Mockito.verify(userRepository, Mockito.never()).delete(entity);
    }

    @Test
    void shouldListAllUsers() {
        List<UserEntity> userList = List.of(entity);
        Page<UserEntity> userPage = new PageImpl<>(userList);

        Mockito.when(userRepository.findAll(PageRequest.of(1, 5))).thenReturn(userPage);
        Mockito.when(mapper.toResponse(Mockito.any())).thenReturn(userResponseDTO);

        var list = userService.findAllUsers(1,5);

        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("Victor", list.get(0).name());
    }

    @Test
    void shouldFindById() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));
        Mockito.when(mapper.toResponse(Mockito.any())).thenReturn(userResponseDTO);

        var response = userService.findById(1L);

        Assertions.assertNotNull(response);
        Mockito.verify(userRepository).findById(Mockito.any());
    }

    @Test
    void shouldThrowErroWhenFindById(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findById(1L));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}