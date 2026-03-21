package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.request.ExpenseRequestDTO;
import com.devvictorh.cashflow.dto.response.ExpenseResponseDTO;
import com.devvictorh.cashflow.entity.CategoryEntity;
import com.devvictorh.cashflow.entity.ExpenseEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.CategoryType;
import com.devvictorh.cashflow.exceptions.BusinessException;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.repository.CategoryRepository;
import com.devvictorh.cashflow.repository.ExpenseRepository;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.service.mapper.ExpenseMapper;
import com.devvictorh.cashflow.validator.ExpenseValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    ExpenseEntity expenseEntity;
    ExpenseRequestDTO expenseRequestDTO;
    ExpenseResponseDTO expenseResponseDTO;
    UserEntity user;
    CategoryEntity categoryEntity;

    @InjectMocks
    ExpenseService service;

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ExpenseMapper mapper;

    @Mock
    ExpenseValidator validator;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        expenseRequestDTO= new ExpenseRequestDTO("Comida", BigDecimal.valueOf(50),1L);
        expenseResponseDTO = new ExpenseResponseDTO(1L, "Comida",BigDecimal.valueOf(50));

        user = new UserEntity();
        user.setId(1L);
        user.setName("Victor");
        user.setEmail("victor@gmail.com");
        user.setPassword("123");

        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Alimentacao");
        categoryEntity.setType(CategoryType.INCOME);
        categoryEntity.setUserEntity(user);

        expenseEntity = new ExpenseEntity();
        expenseEntity.setId(1L);
        expenseEntity.setCategoryEntity(categoryEntity);
        expenseEntity.setUserEntity(user);
        expenseEntity.setAmount(BigDecimal.valueOf(50));
        expenseEntity.setDescription("Comida");
    }

    @Test
    void shouldCreateExpense() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(categoryEntity));
        Mockito.doNothing().when(validator).validar(Mockito.any(), Mockito.any());
        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(expenseEntity);

        service.createExpense(1L, 1L, expenseRequestDTO);

        Mockito.verify(expenseRepository, Mockito.times(1)).save(expenseEntity);
    }

    @Test
    void shouldThrowErrorWhenCreateExpense() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.createExpense(1L, 1L, expenseRequestDTO));
        Mockito.verify(expenseRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldListAllExpense() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(mapper.toResponseList(Mockito.any())).thenReturn(List.of(expenseResponseDTO));

        var list = service.listAllExpense(1L);

        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("Comida", list.get(0).description());
    }

    @Test
    void shouldThrowErrorWhenListAllExpense(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.listAllExpense(1L));
    }

    @Test
    void shouldDeleteExpense() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(expenseRepository.findById(Mockito.any())).thenReturn(Optional.of(expenseEntity));

        service.deleteExpense(1L, 1L);

        Mockito.verify(expenseRepository, Mockito.times(1)).delete(expenseEntity);
    }

    @Test
    void shouldThrowErrorEntityNotFoundWhenDeleteExpense(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteExpense(1L, 1L));
    }

    @Test
    void shouldThrowErrorBusinessExceptionWhenDeleteExpense(){
        Long userId = 1L;
        Long expenseId = 2L;
        UserEntity anotherUser = new UserEntity();
        anotherUser.setId(4L);

        user.setId(userId);
        expenseEntity.setId(expenseId);
        expenseEntity.setUserEntity(anotherUser);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(expenseRepository.findById(Mockito.any())).thenReturn(Optional.of(expenseEntity));

        Assertions.assertThrows(BusinessException.class, () -> service.deleteExpense(1L, 1L));
        Mockito.verify(expenseRepository,Mockito.never()).delete(expenseEntity);
    }
}