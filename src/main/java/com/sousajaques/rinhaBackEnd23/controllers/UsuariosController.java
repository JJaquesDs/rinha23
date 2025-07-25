package com.sousajaques.rinhaBackEnd23.controllers;


import com.sousajaques.rinhaBackEnd23.implementations.UsuarioBuscaImpl;
import com.sousajaques.rinhaBackEnd23.implementations.UsuariosValidacaoImpl;
import com.sousajaques.rinhaBackEnd23.models.Usuarios;
import com.sousajaques.rinhaBackEnd23.repository.UsuariosRepository;
import com.sousajaques.rinhaBackEnd23.service.UsuariosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsuariosController {

    private final UsuariosService usuariosService;
    private final UsuariosValidacaoImpl usuariosValidacaoImpl;
    private final UsuarioBuscaImpl usuarioBuscaImpl;
    private UsuariosRepository usuariosRepository;

    //tive que adicionar UsuariosRepo no construtor (e aprendi que o Spring n funciona com sobrecarga aqui...)
    @Autowired
    public UsuariosController(UsuariosService usuariosService,
                              UsuariosValidacaoImpl usuariosValidacaoImpl,
                              UsuarioBuscaImpl usuarioBuscaImpl,
                              UsuariosRepository usuariosRepository) {
        this.usuariosService = usuariosService;
        this.usuariosValidacaoImpl = usuariosValidacaoImpl;
        this.usuarioBuscaImpl = usuarioBuscaImpl;
        this.usuariosRepository = usuariosRepository;
    }



    @GetMapping("/listar/{id}")
    public ResponseEntity<Usuarios> listarUsuariosPorId(@PathVariable UUID id){

        Usuarios usuario = usuariosService.listarPorId(id);

        if (usuariosService.listarPorId(id) != null){

            return ResponseEntity.ok(usuario);

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/criar")
    public ResponseEntity<String> salvar(@RequestBody @Valid Usuarios usuario){

        if (usuariosValidacaoImpl.apelidoJaExiste(usuario.getApelido())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Apelido já existe");

        }else{
            usuariosService.bufferUsuarios(usuario);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado");
    }

    /** Para usar essa rota, por padrao o termo de busca deve ser passado em:
     *
     *  <pre> localhost:8080/users/buscaPersonalizada?t=</pre>
     *
     * Esse "serviço" não está em {@link UsuariosService}, está em {@link UsuarioBuscaImpl}(sujeito a refatoração)
     *  **/
    @GetMapping("/buscaPersonalizada")
    public  ResponseEntity<?> buscaPersonalizada(@RequestParam(required = false) String t){

        if (t == null || t.isBlank()){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O parâmetro '?t=' é obrigatório");
        }

        List<Usuarios> resultado = usuarioBuscaImpl.buscaTermo(t);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }

    /** EndPoint especial apenas pra contar o total de registros no banco, por isso sem service e nada.
     *
     * (não será testado)**/
    @GetMapping("/contagem")
    public ResponseEntity<String> contagemUsuarios(){
        long quantidade = usuariosRepository.count();

        return ResponseEntity.ok(String.valueOf(quantidade));
    }
}
