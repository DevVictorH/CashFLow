package com.devvictorh.cashflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    @OneToMany(mappedBy = "user")
    private List<Money> moneyList;

    @OneToMany(mappedBy = "user")
    private List<Expanse> expanses;

    @OneToMany(mappedBy = "user")
    private List<Goal> goals;

}
