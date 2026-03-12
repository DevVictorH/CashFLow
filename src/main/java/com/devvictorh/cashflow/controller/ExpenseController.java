package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.ExpenseRequestDTO;
import com.devvictorh.cashflow.dto.response.ExpenseResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@AllArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid ExpenseRequestDTO dto){
        try {
            service.createExpense(user.getId(), dto.categoryId(), dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> list(@AuthenticationPrincipal UserEntity user){
        try {
            List<ExpenseResponseDTO> list = service.listAllExpense(user.getId());
            return ResponseEntity.ok(list);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserEntity user, @PathVariable Long expenseId) {
        try {
            service.deleteExpense(user.getId(), expenseId);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
