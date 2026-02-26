package com.devvictorh.cashflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tb_users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "userEntity")
    private List<CategoryEntity> categories;

    @OneToMany(mappedBy = "userEntity")
    private List<IncomeEntity> incomeEntityList;

    @OneToMany(mappedBy = "userEntity")
    private List<ExpenseEntity> expans;

    @OneToMany(mappedBy = "userEntity")
    private List<GoalEntity> goalEntities;

}
