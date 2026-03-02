package com.devvictorh.cashflow.entity;

import com.devvictorh.cashflow.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_users")
@Data
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private UserRole role;

    @OneToMany(mappedBy = "userEntity")
    private List<CategoryEntity> categories;

    @OneToMany(mappedBy = "userEntity")
    private List<IncomeEntity> incomeEntityList;

    @OneToMany(mappedBy = "userEntity")
    private List<ExpenseEntity> expans;

    @OneToMany(mappedBy = "userEntity")
    private List<GoalEntity> goalEntities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return "";
    }
}
