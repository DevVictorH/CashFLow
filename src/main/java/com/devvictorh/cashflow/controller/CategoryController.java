package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.CategoryRequestDTO;
import com.devvictorh.cashflow.dto.response.CategoryResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    @Operation(summary = "Salvar Categoria", description = "Cria uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!")
    })
    public ResponseEntity<Void> save(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid CategoryRequestDTO dto){
        service.createCategory(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @Operation(summary = "Atualizar Categoria", description = "Atualiza uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor!")
    })
    public ResponseEntity<Void> update(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid CategoryRequestDTO dto){
            service.updateCategory(user.getId(), dto);
            return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar Categorias", description = "Lista todas as categorias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categorias encontrada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!")
    })
    public ResponseEntity<List<CategoryResponseDTO>> list(@AuthenticationPrincipal UserEntity user){
            List<CategoryResponseDTO> list = service.listAllCategories(user.getId());
            return ResponseEntity.ok(list);
    }

    @DeleteMapping
    @Operation(summary = "Deletar Categoria pelo ID", description = "Deleta uma categoria pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria deletada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor!")
    })
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserEntity user) {
            service.deleteCategory(user.getId());
            return ResponseEntity.noContent().build();
    }
}
