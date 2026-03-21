package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.ExpenseRequestDTO;
import com.devvictorh.cashflow.dto.response.ExpenseResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.service.ExpenseService;
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

@Tag(name = "Expense", description = "Gerenciamento de despesas")
@RestController
@AllArgsConstructor
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    @Operation(summary = "Salvar Despesa", description = "Cria uma despesa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Despesa criada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!")
    })
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid ExpenseRequestDTO dto){
            service.createExpense(user.getId(), dto.categoryId(), dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Listar Despesas", description = "Lista todas as despesas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesas encontrada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!")
    })
    public ResponseEntity<List<ExpenseResponseDTO>> list(@AuthenticationPrincipal UserEntity user){
            List<ExpenseResponseDTO> list = service.listAllExpense(user.getId());
            return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{expenseId}")
    @Operation(summary = "Deletar Despesa pelo ID", description = "Deleta uma despesa pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa deletada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor!")
    })
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserEntity user, @PathVariable Long expenseId) {
            service.deleteExpense(user.getId(), expenseId);
            return ResponseEntity.noContent().build();
    }

}
