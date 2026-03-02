package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.CategoryRequestDTO;
import com.devvictorh.cashflow.dto.response.CategoryResponseDTO;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;

    // Estou usando o pathVariable por enquanto pois o User será passado pelo token e não tenho token ainda.
    @PostMapping
    public ResponseEntity<Void> save(@PathVariable Long userId, @RequestBody @Valid CategoryRequestDTO dto){
        service.createCategory(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable Long userId, @RequestBody @Valid CategoryRequestDTO dto){
        try {
            service.updateCategory(userId, dto);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> list(@PathVariable Long userId){
        try {
            List<CategoryResponseDTO> list = service.listAllCategories(userId);
            return ResponseEntity.ok(list);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        try {
            service.deleteCategory(userId);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
