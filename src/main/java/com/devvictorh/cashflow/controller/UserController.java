package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.UserRequestDTO;
import com.devvictorh.cashflow.dto.response.UserResponseDTO;
import com.devvictorh.cashflow.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserRequestDTO dto){
        service.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

   @GetMapping
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> listAll(){
        return ResponseEntity.ok(service.listAllUsers());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long userId){
            var usuarioEncontrado = service.findById(userId);
            return ResponseEntity.ok(usuarioEncontrado);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long userId, @RequestBody @Valid UserRequestDTO dto){
            var savedUser = service.updateUser(userId, dto);
            return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long userId){
            service.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
