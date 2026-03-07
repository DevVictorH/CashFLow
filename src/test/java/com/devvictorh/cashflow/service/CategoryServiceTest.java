package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.request.CategoryRequestDTO;
import com.devvictorh.cashflow.dto.response.CategoryResponseDTO;
import com.devvictorh.cashflow.entity.CategoryEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.CategoryType;
import com.devvictorh.cashflow.exceptions.BusinessException;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.repository.CategoryRepository;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.service.mapper.CategoryMapper;
import com.devvictorh.cashflow.validator.CategoryValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    CategoryEntity categoryEntity;
    CategoryResponseDTO categoryResponseDTO;
    CategoryRequestDTO categoryRequestDTO;
    UserEntity user;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService service;

    @Mock
    CategoryValidator validator;

    @Mock
    CategoryMapper mapper;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp(){
        categoryRequestDTO = new CategoryRequestDTO("Lazer", CategoryType.EXPENSE);
        categoryResponseDTO = new CategoryResponseDTO(1L, "Lazer", CategoryType.EXPENSE);

        user = new UserEntity();
        user.setId(1L);
        user.setName("Victor");
        user.setEmail("victor@gmail.com");
        user.setPassword("123");

        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Lazer");
        categoryEntity.setType(CategoryType.EXPENSE);
        categoryEntity.setUserEntity(user);
    }

    @Test
    void shouldCreateCategory() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(validator).validar(categoryRequestDTO);
        Mockito.doNothing().when(validator).validarCategoriaUnica(categoryRequestDTO, user);
        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(categoryEntity);

        service.createCategory(1L, categoryRequestDTO);

        Mockito.verify(categoryRepository, Mockito.times(1)).save(categoryEntity);
    }

    @Test
    void shouldThrowErrorWhenCreateCategory(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.createCategory(1L, categoryRequestDTO));
        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldUpdateCategory() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(categoryEntity));
        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(categoryEntity);

        service.updateCategory(1L, categoryRequestDTO);

        Mockito.verify(categoryRepository, Mockito.times(1)).save(categoryEntity);
    }

    @Test
    void shouldThrowErrorWhenUpdateCategory(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.updateCategory(1L, categoryRequestDTO));
    }

    @Test
    void shouldListAllCategories() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(mapper.toResponseList(Mockito.any())).thenReturn(List.of(categoryResponseDTO));

        var list = service.listAllCategories(1L);

        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("Lazer", list.get(0).name());
    }

    @Test
    void shouldThrowErrorWhenListAllCategories(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.listAllCategories(1L));
    }

    @Test
    void shouldDeleteCategory() {
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(categoryEntity));

        service.deleteCategory(1L);

        Mockito.verify(categoryRepository, Mockito.times(1)).delete(categoryEntity);
    }

    @Test
    void shouldThrowErrorWhenDeleteCategory(){
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.deleteCategory(1L));

    }
}