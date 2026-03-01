package com.devvictorh.cashflow.validator;

import com.devvictorh.cashflow.dto.CategoryRequestDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.exceptions.BusinessException;
import com.devvictorh.cashflow.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {

    @Autowired
    private CategoryRepository repository;


    public void validar(CategoryRequestDTO dto){
        if(dto.name() == null || dto.name().isBlank()){
            throw new BusinessException("Nome da categoria é obrigatório");
        }

        if(dto.type() == null){
            throw new BusinessException("Tipo da categoria é obrigatório");
        }
    }

    public void validarCategoriaUnica(CategoryRequestDTO dto, UserEntity user){
        boolean exists = repository.existsByNameAndUserEntityAndType(dto.name(), user, dto.type());

        if(exists){
            throw new BusinessException("Já existe uma categoria com esse nome para este usuário");
        }
    }
}
