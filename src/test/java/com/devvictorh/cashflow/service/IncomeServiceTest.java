package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.request.IncomeRequestDTO;
import com.devvictorh.cashflow.dto.response.IncomeResponseDTO;
import com.devvictorh.cashflow.entity.CategoryEntity;
import com.devvictorh.cashflow.entity.IncomeEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.CategoryType;
import com.devvictorh.cashflow.exceptions.BusinessException;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.repository.CategoryRepository;
import com.devvictorh.cashflow.repository.IncomeRepository;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.service.mapper.IncomeMapper;
import com.devvictorh.cashflow.validator.IncomeValidator;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IncomeServiceTest {

    IncomeEntity incomeEntity;
    IncomeRequestDTO incomeRequestDTO;
    UserEntity user;
    CategoryEntity categoryEntity;
    IncomeResponseDTO incomeResponseDTO;

    @Mock
    IncomeRepository incomeRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    IncomeService service;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    IncomeMapper mapper;

    @Mock
    IncomeValidator validator;

    @BeforeEach
    void setUp(){
        incomeRequestDTO = new IncomeRequestDTO("adiantamento", BigDecimal.valueOf(50),1L);
        incomeResponseDTO = new IncomeResponseDTO(1L, "adiantamento",BigDecimal.valueOf(50));

        user = new UserEntity();
        user.setId(1L);
        user.setName("Victor");
        user.setEmail("victor@gmail.com");
        user.setPassword("123");

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
    void shouldCreateIncome() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(categoryEntity));
        Mockito.doNothing().when(validator).validar(Mockito.any(), Mockito.any());
        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(incomeEntity);

        service.createIncome(1L, 1L,incomeRequestDTO);

        Mockito.verify(incomeRepository, Mockito.times(1)).save(incomeEntity);

    }

    @Test
    void shouldThrowErrorWhenCreateIncome() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.createIncome(1L, 1L, incomeRequestDTO));
        Mockito.verify(incomeRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldListAllIncome() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(mapper.toResponseList(Mockito.any())).thenReturn(List.of(incomeResponseDTO));

        var list = service.listAllIncome(1L);

        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("adiantamento", list.get(0).description());
    }

    @Test
    void shouldThrowErrorWhenListAllIncome(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.listAllIncome(1L));
    }

    @Test
    void shouldDeleteIncome() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(incomeRepository.findById(Mockito.any())).thenReturn(Optional.of(incomeEntity));

        service.deleteIncome(1L, 1L);

        Mockito.verify(incomeRepository).delete(Mockito.any());
    }

    @Test
    void shouldThrowErrorObjectNotFoundWhenDeleteIncome(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.deleteIncome(1L, 1L));
    }

    @Test
    void shouldThrowErrorBusinessExceptionWhenDeleteIncome(){
        Long userId = 1L;
        Long incomeId = 10L;
        UserEntity anotherUser = new UserEntity();
        anotherUser.setId(4L);

        user.setId(userId);
        incomeEntity.setId(incomeId);
        incomeEntity.setUserEntity(anotherUser);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(incomeRepository.findById(Mockito.any())).thenReturn(Optional.of(incomeEntity));


        Assertions.assertThrows(BusinessException.class, () -> service.deleteIncome(userId, incomeId));
        Mockito.verify(incomeRepository, Mockito.never()).delete(Mockito.any());
    }

}