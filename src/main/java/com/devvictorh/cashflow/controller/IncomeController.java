package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.IncomeRequestDTO;
import com.devvictorh.cashflow.dto.response.IncomeResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.service.IncomeService;
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
@RequestMapping("/api/incomes")
@AllArgsConstructor
@Tag(name = "Income")
public class IncomeController {

    private final IncomeService service;

    @PostMapping
    @Operation(summary = "Salvar Renda", description = "Cria uma renda")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Renda criada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!")
    })
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid IncomeRequestDTO dto){
            service.createIncome(user.getId(), dto.categoryId(), dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Listar Rendas", description = "Lista todas as rendas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rendas encontrada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!")
    })
    public ResponseEntity<List<IncomeResponseDTO>> list(@AuthenticationPrincipal UserEntity user){
            List<IncomeResponseDTO> list = service.listAllIncome(user.getId());
            return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{expenseId}")
    @Operation(summary = "Deletar Renda pelo ID", description = "Deleta a renda pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Renda deletada!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "404", description = "Renda não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor!")
    })
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserEntity user, @PathVariable Long expenseId) {
            service.deleteIncome(user.getId(), expenseId);
            return ResponseEntity.noContent().build();
    }
}
