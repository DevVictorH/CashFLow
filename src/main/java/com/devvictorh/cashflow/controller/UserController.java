package com.devvictorh.cashflow.controller;

import com.devvictorh.cashflow.dto.UserRequestDTO;
import com.devvictorh.cashflow.dto.UserResponseDTO;
import com.devvictorh.cashflow.service.UserService;
import com.devvictorh.cashflow.service.mapper.UserMapper;
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
    public ResponseEntity<Void> save(@RequestBody UserRequestDTO dto){
        service.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

   @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll(){
        return ResponseEntity.ok(service.listAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        var usuarioEncontrado = service.findById(id);
        return ResponseEntity.ok(usuarioEncontrado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UserRequestDTO dto){
        service.updateUser(id, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
