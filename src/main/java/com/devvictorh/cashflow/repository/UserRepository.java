package com.devvictorh.cashflow.repository;

import com.devvictorh.cashflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
