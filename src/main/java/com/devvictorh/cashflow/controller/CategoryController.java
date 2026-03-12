package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.CategoryRequestDTO;
import com.devvictorh.cashflow.dto.response.CategoryResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.service.CategoryService;
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
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;

    // Estou usando o pathVariable por enquanto pois o User será passado pelo token e não tenho token ainda.
    @PostMapping
    public ResponseEntity<Void> save(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid CategoryRequestDTO dto){
        service.createCategory(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid CategoryRequestDTO dto){
        try {
            service.updateCategory(user.getId(), dto);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> list(@AuthenticationPrincipal UserEntity user){
        try {
            List<CategoryResponseDTO> list = service.listAllCategories(user.getId());
            return ResponseEntity.ok(list);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserEntity user) {
        try {
            service.deleteCategory(user.getId());
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
