package com.sousajaques.rinhaBackEnd23.service;

import com.sousajaques.rinhaBackEnd23.models.Usuarios;
import com.sousajaques.rinhaBackEnd23.repository.UsuariosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.*;


@Service
public class UsuariosService{



    /** Instância de {@link UsuariosRepository} para trabalhar com as persistências do Spring Data JPA**/
    private final UsuariosRepository usuariosRepository;


    // Construtor
    @Autowired
    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }





    public Usuarios salvar(@Valid Usuarios usuario){

        if (usuariosRepository.existsByApelido(usuario.getApelido())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return usuariosRepository.save(usuario);

    }



    /** Listar {@link Usuarios} pelo Id
     *
     * <p> Caso não haja retorna nulo para lidar com a excessão no Controller</p>**/
    public Usuarios listarPorId(UUID id){
        Optional<Usuarios> usuarioId = usuariosRepository.findById(id);

        return usuarioId.orElse(null);
    }


}
