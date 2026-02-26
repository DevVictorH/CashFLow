package com.devvictorh.cashflow.repository;

import com.devvictorh.cashflow.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpanseRepository extends JpaRepository<ExpenseEntity, Long> {
}
