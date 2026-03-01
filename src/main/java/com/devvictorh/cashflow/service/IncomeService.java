package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.IncomeRequestDTO;
import com.devvictorh.cashflow.entity.ExpenseEntity;
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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IncomeService {

    private final IncomeRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final IncomeMapper mapper;
    private final IncomeValidator validator;

    public void createIncome(Long userId, Long categoryId, IncomeRequestDTO dto){
        var user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));

        validator.validar(user, category);

        var mappedIncome = mapper.toEntity(dto);
        mappedIncome.setCategoryEntity(category);
        mappedIncome.setUserEntity(user);

        repository.save(mappedIncome);
    }

    public List<IncomeEntity> listAllIncome(Long userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        return repository.findByUserEntity(user);
    }

    public void deleteIncome(Long userId, Long incomeId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        var incomeFound = repository.findById(incomeId)
                .orElseThrow(() -> new ObjectNotFoundException("Receita não encontrada"));

        if(!incomeFound.getUserEntity().getId().equals(userId)){
            throw new BusinessException("Receita não pertence ao usuário");
        }
        repository.delete(incomeFound);
    }


}
