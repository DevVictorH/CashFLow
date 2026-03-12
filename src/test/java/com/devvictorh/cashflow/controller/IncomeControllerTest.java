package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.IncomeRequestDTO;
import com.devvictorh.cashflow.dto.response.IncomeResponseDTO;
import com.devvictorh.cashflow.entity.CategoryEntity;
import com.devvictorh.cashflow.entity.IncomeEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.CategoryType;
import com.devvictorh.cashflow.entity.enums.UserRole;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.repository.CategoryRepository;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.security.TokenService;
import com.devvictorh.cashflow.service.IncomeService;
import com.devvictorh.cashflow.service.mapper.IncomeMapper;
import com.devvictorh.cashflow.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IncomeController.class)
@AutoConfigureMockMvc
class IncomeControllerTest {


    IncomeRequestDTO incomeRequestDTO;
    IncomeResponseDTO incomeResponseDTO;
    IncomeEntity incomeEntity;
    UserEntity user;
    CategoryEntity categoryEntity;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    IncomeService service;

    @MockitoBean
    TokenService tokenService;

    @MockitoBean
    IncomeMapper mapper;

    @MockitoBean
    UserRepository userRepository;

    @BeforeEach
    void setUp(){
        incomeRequestDTO = new IncomeRequestDTO("adiantamento", BigDecimal.valueOf(50),1L);
        incomeResponseDTO = new IncomeResponseDTO(1L, "adiantamento",BigDecimal.valueOf(50));

        user = new UserEntity();
        user.setId(1L);
        user.setName("Victor");
        user.setEmail("victor@gmail.com");
        user.setPassword("123");
        user.setRole(UserRole.ADMIN);

        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Salario");
        categoryEntity.setType(CategoryType.INCOME);
        categoryEntity.setUserEntity(user);

        incomeEntity = new IncomeEntity();
        incomeEntity.setId(1L);
        incomeEntity.setCategoryEntity(categoryEntity);
        incomeEntity.setUserEntity(user);
        incomeEntity.setAmount(BigDecimal.valueOf(50));
        incomeEntity.setDescription("adiantamento");

    }

    @Test
    void shouldCreate() throws Exception{
        Mockito.doNothing().when(service).createIncome(Mockito.anyLong(),Mockito.anyLong(),Mockito.any());

        String json = objectMapper.writeValueAsString(incomeRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.post("/api/incomes")
                        .with(user(user))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldList() throws Exception{
        Mockito.when(service.listAllIncome(Mockito.any())).thenReturn(List.of(incomeResponseDTO));

        mvc.perform(
                MockMvcRequestBuilders.get("/api/incomes")
                        .with(user(user))
                        .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    void shouldDelete() throws Exception{
        Mockito.doNothing().when(service).deleteIncome(Mockito.anyLong(),Mockito.anyLong());

        mvc.perform(
                MockMvcRequestBuilders.delete("/api/incomes/1")
                        .with(user(user))
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    void shouldThrowErrorWhenDelete() throws Exception{
        Mockito.doThrow(ObjectNotFoundException.class).when(service).deleteIncome(Mockito.anyLong(),Mockito.anyLong());

        mvc.perform(
                MockMvcRequestBuilders.delete("/api/incomes/5")
                        .with(user(user))
                        .with(csrf())
        ).andExpect(status().isNotFound());
    }
}