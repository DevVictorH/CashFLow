package com.devvictorh.cashflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
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

    @OneToMany(mappedBy = "user")
    private List<CategoryEntity> categories;

    @OneToMany(mappedBy = "user")
    private List<IncomeEntity> incomeEntityList;

    @OneToMany(mappedBy = "user")
    private List<ExpanseEntity> expans;

    @OneToMany(mappedBy = "user")
    private List<GoalEntity> goalEntities;

}
