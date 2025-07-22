package com.sousajaques.rinhaBackEnd23.implementations;

import com.sousajaques.rinhaBackEnd23.repository.UsuariosRepository;
import com.sousajaques.rinhaBackEnd23.validation.UsuariosValidacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuariosValidacaoImpl implements UsuariosValidacao {

    private final UsuariosRepository usuariosRepository;

    @Autowired
    public UsuariosValidacaoImpl(UsuariosRepository usuariosRepository){
        this.usuariosRepository = usuariosRepository;
    }


    @Override
    public boolean apelidoJaExiste(String apelido){
        return usuariosRepository.existsByApelido(apelido);
    }
}
