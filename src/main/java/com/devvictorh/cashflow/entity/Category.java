package com.devvictorh.cashflow.entity;

import com.devvictorh.cashflow.entity.enums.CategoryType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "category")
    private List<Money> moneyList;

    @OneToMany(mappedBy = "category")
    private List<Expanse> expanses;

    @OneToMany(mappedBy = "category")
    private List<Goal> goals;

}
