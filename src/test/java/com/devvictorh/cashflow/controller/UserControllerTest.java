package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.UserRequestDTO;
import com.devvictorh.cashflow.dto.response.UserResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.UserRole;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.security.TokenService;
import com.devvictorh.cashflow.service.UserService;
import com.devvictorh.cashflow.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    UserEntity user;
    UserResponseDTO response;
    UserRequestDTO requestDTO;

    @MockitoBean
    UserService service;

    @Autowired
    MockMvc mvc;

    @MockitoBean
    TokenService tokenService;

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    UserMapper mapper;

    @BeforeEach
    void setUp(){

        response = new UserResponseDTO(1L, "Victor", "victor@gmail.com");
        requestDTO = new UserRequestDTO("Lucas", "lucas@gmail.com", "321","ADMIN");

        user = new UserEntity();
        user.setId(1L);
        user.setName("Victor");
        user.setEmail("victor@gmail.com");
        user.setPassword("123");
        user.setRole(UserRole.ADMIN);
    }

    @Test
    void save() throws Exception{
        Mockito.when(service.saveUser(Mockito.any())).thenReturn(user);

        String json = """
                {
                    "name":"Victor",
                    "email":"victor@gmail.com",
                    "password":"123",
                    "role":"ADMIN"
                }
                """;
        ResultActions result = mvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        result.andExpect(status().isCreated());
    }

    @Test
    void listAll() throws Exception{
        Mockito.when(mapper.toResponse(user)).thenReturn(response);
        Mockito.when(service.listAllUsers()).thenReturn(List.of(response));

        mvc.perform(
                get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Victor"))
                .andExpect(jsonPath("$[0].email").value("victor@gmail.com"));

    }

    @Test
    void findById() throws Exception{
        Mockito.when(service.findById(1L)).thenReturn(response);
        Mockito.when(mapper.toResponse(Mockito.any())).thenReturn(response);

        mvc.perform(
                get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update() throws Exception{
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Lucas", "lucas@gmail.com");
        Mockito.when(service.updateUser(Mockito.eq(1L), Mockito.any())).thenReturn(userResponseDTO);
        Mockito.when(mapper.toEntity(requestDTO)).thenReturn(user);
        Mockito.when(mapper.toResponse(user)).thenReturn(userResponseDTO);

        String json = """
                {
                    "name":"Lucas",
                    "email":"lucas@gmail.com",
                    "password":"321",
                    "role":"ADMIN"
                }
                """;

        ResultActions result = mvc.perform(
                put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").value("Lucas"));
    }

    @Test
    void delete() throws Exception{
        Mockito.doNothing().when(service).deleteUser(Mockito.any());

        mvc.perform(
                MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(status().isOk());

    }
}