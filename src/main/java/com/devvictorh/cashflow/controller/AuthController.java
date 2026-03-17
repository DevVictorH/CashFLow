package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.AuthenticationRequestDTO;
import com.devvictorh.cashflow.dto.request.UserRequestDTO;
import com.devvictorh.cashflow.dto.response.LoginResponseDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.security.TokenService;
import com.devvictorh.cashflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final UserService service;
    private final TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Logar Usuario", description = "Faz o login de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido")
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO dto){
        var emailAndPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authenticationManager.authenticate(emailAndPassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar Usuario", description = "Faz o registo de um usuário novo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Argumento inválido")
    })
    public ResponseEntity<Void> register(@RequestBody UserRequestDTO dto){
            service.saveUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
