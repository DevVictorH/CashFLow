package com.devvictorh.cashflow.repository;

import com.devvictorh.cashflow.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
