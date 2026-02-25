package com.devvictorh.cashflow.entity;

import com.devvictorh.cashflow.entity.enums.CategoryType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "category")
@Data
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "category")
    private List<IncomeEntity> incomeEntityList;

    @OneToMany(mappedBy = "category")
    private List<ExpanseEntity> expans;

    @OneToMany(mappedBy = "category")
    private List<GoalEntity> goalEntities;

}
