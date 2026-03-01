package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.CategoryRequestDTO;
import com.devvictorh.cashflow.dto.CategoryResponseDTO;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;

    // Estou usando o pathVariable por enquanto pois o User será passado pelo token e não tenho token ainda.
    @PostMapping("{userId}")
    public ResponseEntity<Void> save(@PathVariable Long userId, @RequestBody CategoryRequestDTO dto){
        service.createCategory(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{userId}")
    public ResponseEntity<Void> update(@PathVariable Long userId, @RequestBody CategoryRequestDTO dto){
        try {
            service.updateCategory(userId, dto);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<CategoryResponseDTO>> list(@PathVariable Long userId){
        try {
            List<CategoryResponseDTO> list = service.listAllCategories(userId);
            return ResponseEntity.ok(list);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
