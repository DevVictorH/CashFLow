package com.devvictorh.cashflow.repository;

import com.devvictorh.cashflow.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
}
