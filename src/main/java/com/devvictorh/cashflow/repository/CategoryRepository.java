package com.devvictorh.cashflow.repository;

import com.devvictorh.cashflow.entity.CategoryEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.entity.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByNameAndUserEntityAndType(String name, UserEntity entity, CategoryType type);

    Long id(Long id);
}
