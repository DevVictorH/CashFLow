package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.IncomeRequestDTO;
import com.devvictorh.cashflow.dto.IncomeResponseDTO;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.service.IncomeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@AllArgsConstructor
public class IncomeController {

    private final IncomeService service;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> create(@PathVariable Long userId, @RequestBody IncomeRequestDTO dto){
        try {
            service.createIncome(userId, dto.categoryId(), dto);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<IncomeResponseDTO>> list(@PathVariable Long userId){
        try {
            List<IncomeResponseDTO> list = service.listAllIncome(userId);
            return ResponseEntity.ok(list);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{userId}/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long expenseId) {
        try {
            service.deleteIncome(userId, expenseId);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
