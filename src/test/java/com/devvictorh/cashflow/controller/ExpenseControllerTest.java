package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.ExpenseRequestDTO;
import com.devvictorh.cashflow.dto.response.ExpenseResponseDTO;
import com.devvictorh.cashflow.entity.CategoryEntity;
import com.devvictorh.cashflow.entity.ExpenseEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.CategoryType;
import com.devvictorh.cashflow.entity.enums.UserRole;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.repository.ExpenseRepository;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.security.TokenService;
import com.devvictorh.cashflow.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc
class ExpenseControllerTest {

    ExpenseEntity expenseEntity;
    ExpenseRequestDTO expenseRequestDTO;
    ExpenseResponseDTO expenseResponseDTO;
    UserEntity user;
    CategoryEntity categoryEntity;

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ExpenseRepository expenseRepository;

    @MockitoBean
    ExpenseService service;

    @MockitoBean
    TokenService tokenService;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserRepository userRepository;

    @BeforeEach
    void setUp(){
        expenseRequestDTO= new ExpenseRequestDTO("Comida", BigDecimal.valueOf(50),1L);
        expenseResponseDTO = new ExpenseResponseDTO(1L, "Comida",BigDecimal.valueOf(50));

        user = new UserEntity();
        user.setId(1L);
        user.setName("Victor");
        user.setEmail("victor@gmail.com");
        user.setPassword("123");
        user.setRole(UserRole.USER);

        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Alimentacao");
        categoryEntity.setType(CategoryType.EXPENSE);
        categoryEntity.setUserEntity(user);

        expenseEntity = new ExpenseEntity();
        expenseEntity.setId(1L);
        expenseEntity.setCategoryEntity(categoryEntity);
        expenseEntity.setUserEntity(user);
        expenseEntity.setAmount(BigDecimal.valueOf(50));
        expenseEntity.setDescription("Comida");
    }

    @Test
    void shouldCreate() throws Exception{
        Mockito.doNothing().when(service).createExpense(Mockito.anyLong(),Mockito.anyLong(),Mockito.any());

        String json = objectMapper.writeValueAsString(expenseRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.post("/api/expenses")
                        .with(user(user))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldList() throws Exception{
        Mockito.when(service.listAllExpense(Mockito.anyLong())).thenReturn(List.of(expenseResponseDTO));

        mvc.perform(
                MockMvcRequestBuilders.get("/api/expenses")
                        .with(user(user))
                        .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    void shouldDelete() throws Exception{
        Mockito.doNothing().when(service).deleteExpense(Mockito.anyLong(),Mockito.anyLong());

        mvc.perform(
                MockMvcRequestBuilders.delete("/api/expenses/1")
                        .with(user(user))
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    void shouldThrowErrorWhenDelete() throws Exception{
        Mockito.doThrow(ObjectNotFoundException.class).when(service).deleteExpense(Mockito.anyLong(),Mockito.anyLong());

        mvc.perform(
                MockMvcRequestBuilders.delete("/api/expenses/5")
                        .with(user(user))
                        .with(csrf())
        ).andExpect(status().isNotFound());
    }
}