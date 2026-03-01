package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.ExpenseRequestDTO;
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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseService {

    private final ExpenseRepository repository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper mapper;
    private final ExpenseValidator validator;


    public void createExpense(Long userId, Long categoryId, ExpenseRequestDTO dto){
        var user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));

        validator.validar(user, category);

        var mappedExpense = mapper.toEntity(dto);
        mappedExpense.setCategoryEntity(category);
        mappedExpense.setUserEntity(user);

        repository.save(mappedExpense);
    }

    public List<ExpenseEntity> listAllExpense(Long userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        return repository.findByUserEntity(user);
    }

    public void deleteExpense(Long userId, Long expenseId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        var expenseFound = repository.findById(expenseId)
                .orElseThrow(() -> new ObjectNotFoundException("Despesa não encontrada"));

        if(!expenseFound.getUserEntity().getId().equals(userId)){
            throw new BusinessException("Despesa não pertence ao usuário");
        }
        repository.delete(expenseFound);
    }

}
