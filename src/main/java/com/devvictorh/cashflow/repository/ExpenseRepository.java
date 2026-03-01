package com.devvictorh.cashflow.repository;

import com.devvictorh.cashflow.entity.ExpenseEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findByUserEntity(UserEntity user);
}
