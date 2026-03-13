package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.CategoryRequestDTO;
import com.devvictorh.cashflow.dto.response.CategoryResponseDTO;
import com.devvictorh.cashflow.entity.CategoryEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.CategoryType;
import com.devvictorh.cashflow.entity.enums.UserRole;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.security.TokenService;
import com.devvictorh.cashflow.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    CategoryRequestDTO categoryRequestDTO;
    CategoryResponseDTO categoryResponseDTO;
    CategoryEntity categoryEntity;
    UserEntity user;

    @Autowired
    MockMvc mvc;

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    CategoryService service;

    @MockitoBean
    TokenService tokenService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        categoryRequestDTO = new CategoryRequestDTO("Lazer", CategoryType.EXPENSE);
        categoryResponseDTO = new CategoryResponseDTO(1L, "Lazer", CategoryType.EXPENSE);

        user = new UserEntity();
        user.setId(1L);
        user.setName("Victor");
        user.setEmail("victor@gmail.com");
        user.setPassword("123");
        user.setRole(UserRole.USER);

        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Lazer");
        categoryEntity.setType(CategoryType.EXPENSE);
        categoryEntity.setUserEntity(user);
    }

    @Test
    void shouldSave() throws Exception{
        Mockito.doNothing().when(service).createCategory(Mockito.anyLong(),Mockito.any());

        String json = objectMapper.writeValueAsString(categoryRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.post("/api/categories")
                        .with(user(user))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated());

    }

    @Test
    void shouldUpdate() throws Exception{
        Mockito.doNothing().when(service).updateCategory(Mockito.anyLong(),Mockito.any());

        String json = objectMapper.writeValueAsString(categoryRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.put("/api/categories")
                        .with(user(user))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNoContent());
    }

    @Test
    void shouldThrowErrorWhenUpdate() throws Exception{
        Mockito.doThrow(ObjectNotFoundException.class).when(service).updateCategory(Mockito.anyLong(),Mockito.any());

        String json = objectMapper.writeValueAsString(categoryRequestDTO);

        mvc.perform(
                MockMvcRequestBuilders.put("/api/categories")
                        .with(user(user))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldList() throws Exception{
        Mockito.when(service.listAllCategories(Mockito.anyLong())).thenReturn(List.of(categoryResponseDTO));

        mvc.perform(
                MockMvcRequestBuilders.get("/api/categories")
                        .with(user(user))
                        .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    void shouldDelete() throws Exception{
        Mockito.doNothing().when(service).deleteCategory(Mockito.anyLong());

        mvc.perform(
                MockMvcRequestBuilders.delete("/api/categories")
                        .with(user(user))
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    void shouldThrowErrorWhenDelete() throws Exception{
        Mockito.doThrow(ObjectNotFoundException.class).when(service).deleteCategory(Mockito.anyLong());

        mvc.perform(
                MockMvcRequestBuilders.delete("/api/categories")
                        .with(user(user))
                        .with(csrf())
        ).andExpect(status().isNotFound());
    }
}