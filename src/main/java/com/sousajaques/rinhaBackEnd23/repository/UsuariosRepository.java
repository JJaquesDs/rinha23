package com.sousajaques.rinhaBackEnd23.repository;

import com.sousajaques.rinhaBackEnd23.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    boolean existsByApelido(String apelido);

    Optional<Usuarios> findById(UUID id);
}
