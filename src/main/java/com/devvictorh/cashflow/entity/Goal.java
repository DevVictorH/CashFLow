package com.devvictorh.cashflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "goal")
@Data
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double amount;

    private Date date;

    @OneToMany
    private User userId;

    @OneToMany
    private Category categoryId;

}
