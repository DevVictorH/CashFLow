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
    public ResponseEntity<Void> salvar(@RequestBody UserRequestDTO dto){
        service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

   @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id){
        var usuarioEncontrado = service.buscarPorId(id);

        return ResponseEntity.ok(usuarioEncontrado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody UserRequestDTO dto){
        service.atualizar(id, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
