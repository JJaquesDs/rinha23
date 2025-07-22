package com.sousajaques.rinhaBackEnd23.controllers;


import com.sousajaques.rinhaBackEnd23.implementations.UsuariosValidacaoImpl;
import com.sousajaques.rinhaBackEnd23.models.Usuarios;
import com.sousajaques.rinhaBackEnd23.service.UsuariosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsuariosController {

    private final UsuariosService usuariosService;
    private final UsuariosValidacaoImpl usuariosValidacaoImpl;

    @Autowired
    public UsuariosController(UsuariosService usuariosService, UsuariosValidacaoImpl usuariosValidacaoImpl) {
        this.usuariosService = usuariosService;
        this.usuariosValidacaoImpl = usuariosValidacaoImpl;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<String> listarUsuariosPorId(@PathVariable UUID id){

        if (usuariosService.listarPorId(id) != null){

            usuariosService.listarPorId(id);
            return ResponseEntity.ok("Usuário: " + id + "Encontrado");

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário " + id + "não encontrado");
        }
    }


    @PostMapping("/criar")
    public ResponseEntity<String> criarUsuario(@RequestBody @Valid Usuarios usuario){
        usuariosService.bufferUsuarios(usuario);
        if (usuariosValidacaoImpl.apelidoJaExiste(usuario.getApelido())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Apelido já existe");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado");
    }

}
