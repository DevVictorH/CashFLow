package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.CategoryRequestDTO;
import com.devvictorh.cashflow.entity.UserEntity;

public interface CategoryService {

    public void createCategory(CategoryRequestDTO dto, Long userId);

    public void updateCategory(Long id, CategoryRequestDTO dto, UserEntity entity);

    public void deleteCategory(Long id);


}
