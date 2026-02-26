package com.devvictorh.cashflow.entity;

import com.devvictorh.cashflow.entity.enums.CategoryType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tb_categories")
@Data
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "categoryEntity")
    private List<IncomeEntity> incomeEntityList;

    @OneToMany(mappedBy = "categoryEntity")
    private List<ExpenseEntity> expenses;

    @OneToMany(mappedBy = "categoryEntity")
    private List<GoalEntity> goalEntities;

}
