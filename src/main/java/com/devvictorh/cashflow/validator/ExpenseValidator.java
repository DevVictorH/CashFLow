package com.devvictorh.cashflow.validator;

import com.devvictorh.cashflow.entity.CategoryEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.CategoryType;
import com.devvictorh.cashflow.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class ExpenseValidator {

    public void validar(UserEntity user, CategoryEntity category){
        if(category.getType() != CategoryType.INCOME){
            throw new BusinessException("Categoria invalida para despesa");
        }

        if(!category.getUserEntity().getId().equals(user.getId())){
            throw new BusinessException("Categoria não pertence ao usuario");
        }
    }

}
