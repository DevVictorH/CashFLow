package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.IncomeRequestDTO;
import com.devvictorh.cashflow.dto.response.IncomeResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.service.IncomeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@AllArgsConstructor
public class IncomeController {

    private final IncomeService service;

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid IncomeRequestDTO dto){
        try {
            service.createIncome(user.getId(), dto.categoryId(), dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<IncomeResponseDTO>> list(@AuthenticationPrincipal UserEntity user){
        try {
            List<IncomeResponseDTO> list = service.listAllIncome(user.getId());
            return ResponseEntity.ok(list);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserEntity user, @PathVariable Long expenseId) {
        try {
            service.deleteIncome(user.getId(), expenseId);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
