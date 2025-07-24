package com.sousajaques.rinhaBackEnd23.repository;

import com.sousajaques.rinhaBackEnd23.models.Usuarios;

import java.util.List;

public interface UsuarioBuscaRepository {

    List<Usuarios> buscaTermo(String termo);
}
