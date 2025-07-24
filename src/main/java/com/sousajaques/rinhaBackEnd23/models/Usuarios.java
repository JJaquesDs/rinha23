package com.sousajaques.rinhaBackEnd23.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users")
public class Usuarios {


    /** Geração do Id automática com a estratégia de identidade única usando anotações Spring Data **/
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "Nome deve ser fornecido")
    private String nome;

    @Column(length = 32, nullable = false, unique = true)
    @NotBlank(message = "O apelido deve ser fornecido")
    private String apelido;


    @Column(nullable = false)
    @NotNull(message = "A data de Nascimento deve ser fornecida no formato AAAA/MMM/DDD")
    private LocalDate nascimento;


    /** Um {@link Usuarios} pode ter mais de uma stack.
     *
     * <p> {@link CascadeType} significa que todas as alterações feitas em {@link Usuarios} será propagada para {@link Stack}</p>
     *
     * <p> {@link OneToMany} tem o atributo "orphanRemoval" que significa que se for removido um {@link Usuarios},
     * automaticamente {@link Stack} serão removidos também para não ficarem "órfãos" no banco de dados </p>**/
    @OneToMany(mappedBy = "usuarios", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stack> stacks;


    /** Construtor com todos os argumentos **/
    public Usuarios(UUID id, String nome, String apelido, LocalDate nascimento, List<Stack> stacks) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.nascimento = nascimento;
        this.stacks = stacks;
    }


    /** Construtor sem argumentos **/
    public Usuarios(){

    }

    /** Getters e Setters **/
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

    public String getApelido() {
        return apelido;
    }


    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<Stack> getStacks() {
        return stacks;
    }

    public void setStacks(List<Stack> stacks) {
        this.stacks = stacks;
    }
}
