package com.devvictorh.cashflow.repository;

import com.devvictorh.cashflow.entity.IncomeEntity;
import com.devvictorh.cashflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    List<IncomeEntity> findByUserEntity(UserEntity user);

}
