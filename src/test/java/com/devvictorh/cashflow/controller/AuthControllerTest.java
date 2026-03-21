package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.AuthenticationRequestDTO;
import com.devvictorh.cashflow.dto.request.IncomeRequestDTO;
import com.devvictorh.cashflow.dto.request.UserRequestDTO;
import com.devvictorh.cashflow.dto.response.IncomeResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.UserRole;
import com.devvictorh.cashflow.exceptions.BusinessException;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.security.TokenService;
import com.devvictorh.cashflow.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    UserEntity user;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    TokenService tokenService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserRepository repository;

    @MockitoBean
    private UserService service;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setName("Victor");
        user.setEmail("victor@email.com");
        user.setPassword("123");
        user.setRole(UserRole.ADMIN);
    }

    @Test
    void shouldLogin() throws Exception{
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO(
                "victor@email.com","123"
        );
        var authentication = Mockito.mock(org.springframework.security.core.Authentication.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(authentication);

        Mockito.when(tokenService.generateToken(user))
                .thenReturn("fake-token");

        Mockito.when(repository.findByEmail(authenticationRequestDTO.email())).thenReturn(null);

        String json = objectMapper.writeValueAsString(authenticationRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());

    }

    @Test
    void shouldRegister() throws Exception{
        UserRequestDTO userRequestDTO = new UserRequestDTO("Victor", "victor@email.com", "123","ADMIN");

        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(null);

        String json = objectMapper.writeValueAsString(userRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldThrowErrorWhenRegister() throws Exception{
        UserRequestDTO userRequestDTO = new UserRequestDTO("Victor", "victor@email.com", "123","ADMIN");

        Mockito.when(service.saveUser(Mockito.any(UserRequestDTO.class)))
                .thenThrow(new BusinessException("Email já cadastrado"));

        String json = objectMapper.writeValueAsString(userRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }
}