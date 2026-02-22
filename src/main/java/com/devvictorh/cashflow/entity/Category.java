package com.devvictorh.cashflow.entity;

import com.devvictorh.cashflow.entity.enums.CategoryType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private CategoryType type;

    @OneToMany
    private User userId;

}
