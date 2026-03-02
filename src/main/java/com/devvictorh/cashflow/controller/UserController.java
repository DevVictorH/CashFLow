package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.request.UserRequestDTO;
import com.devvictorh.cashflow.dto.response.UserResponseDTO;
import com.devvictorh.cashflow.service.UserService;
import com.devvictorh.cashflow.service.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserRequestDTO dto){
        service.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

   @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll(){
        return ResponseEntity.ok(service.listAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long userId){
        var usuarioEncontrado = service.findById(userId);
        return ResponseEntity.ok(usuarioEncontrado);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> update(@PathVariable Long userId, @RequestBody @Valid UserRequestDTO dto){
        service.updateUser(userId, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId){
        service.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
