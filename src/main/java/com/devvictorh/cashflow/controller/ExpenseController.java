package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.ExpenseRequestDTO;
import com.devvictorh.cashflow.dto.response.ExpenseResponseDTO;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/expenses")
@AllArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable Long userId, @RequestBody @Valid ExpenseRequestDTO dto){
        try {
            service.createExpense(userId, dto.categoryId(), dto);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> list(@PathVariable Long userId){
        try {
            List<ExpenseResponseDTO> list = service.listAllExpense(userId);
            return ResponseEntity.ok(list);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long expenseId) {
        try {
            service.deleteExpense(userId, expenseId);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
