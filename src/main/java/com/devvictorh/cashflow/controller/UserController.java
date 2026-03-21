package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.UserRequestDTO;
import com.devvictorh.cashflow.dto.response.UserResponseDTO;
import com.devvictorh.cashflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "Gerenciamento de usuarios")
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @PostMapping
    @Operation(summary = "Salvar Usuario", description = "Salva um usuário novo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!")
    })
    public ResponseEntity<Void> save(@RequestBody @Valid UserRequestDTO dto){
        service.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

   @GetMapping
   @PreAuthorize("hasRole('ADMIN')")
   @Operation(summary = "Listar Usuarios", description = "Lista todos os usuários")
   @ApiResponses({
           @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
           @ApiResponse(responseCode = "400", description = "Argumento inválido"),
           @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
           @ApiResponse(responseCode = "401", description = "Usuário não autenticado!")
   })
   public ResponseEntity<List<UserResponseDTO>> findAll(@RequestParam int page, @RequestParam int qtyUsers){
       return ResponseEntity.ok(service.findAllUsers(page, qtyUsers));
   }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Pesquisar Usuario por ID", description = "Deve procurar um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!")
    })
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long userId){
            var usuarioEncontrado = service.findById(userId);
            return ResponseEntity.ok(usuarioEncontrado);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar Usuario", description = "Atualiza um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!")
    })
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long userId, @RequestBody @Valid UserRequestDTO dto){
            var savedUser = service.updateUser(userId, dto);
            return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar um Usuario por ID", description = "Deleta um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário deletado!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!")
    })
    public ResponseEntity<Void> delete(@PathVariable Long userId){
            service.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).build();
    }


}
