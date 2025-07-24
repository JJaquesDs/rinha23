package com.sousajaques.rinhaBackEnd23.implementations;


import com.sousajaques.rinhaBackEnd23.models.Usuarios;
import com.sousajaques.rinhaBackEnd23.repository.UsuarioBuscaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/** A classe Implementa o método de {@link UsuarioBuscaRepository#buscaTermo(String)} para fazer o endpoint de busca personalizada, foi introduzido Sql puro para mais performance **/
@Repository
public class UsuarioBuscaImpl implements UsuarioBuscaRepository {


    //instância do JdbcTemplate para lidar com "querys" Sql
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    //construtor
    public UsuarioBuscaImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /** Esse método faz um join entre Stacks e Usuários para retornar a busca pelo termo pesquisado
     *
     * <p> Esse é um dos mais trabalhosos processos de resolver, pq a busca é feita nas colunas das duas tabelas,tanto usuários quanto stack,
     *     esse join das tabelas foi a maneira de menor custo que eu pensei (gostaria de sugestões e esse sql tá sujeito a mudanças futuras).
     *     Escrever sql puro ajuda muito também na performance das querys.
     * </p>**/
    @Override
    public List<Usuarios> buscaTermo(String termo) {

        //script de consultas
        String sql = """
        SELECT DISTINCT u.id, u.nome, u.apelido, u.nascimento
        FROM users u
        LEFT JOIN stack s ON s.usuarios_id = u.id
        WHERE u.nome ILIKE '%' || ? || '%'
           OR u.apelido ILIKE '%' || ? || '%'
           OR s.nome ILIKE '%' || ? || '%'
        LIMIT 50;
    """;

        return jdbcTemplate.query(sql, new Object[]{termo, termo, termo}, new BeanPropertyRowMapper<>(Usuarios.class));
    }


}
