package com.sousajaques.rinhaBackEnd23.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stack")
public class Stack {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 32, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "usuarios_id") //um usuário pode ter muitas stacks, referência por [usuarios_id]
    private Usuarios usuarios;


    /** Construtores com todos os argumentos e sem os argumentos**/
    public Stack(UUID id, String nome, Usuarios usuarios) {
        this.id = id;
        this.nome = nome;
        this.usuarios = usuarios;
    }

    public Stack(){

    }


    /** Getters e Setters**/
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
}
