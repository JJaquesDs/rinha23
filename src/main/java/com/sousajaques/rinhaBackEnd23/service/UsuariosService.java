package com.sousajaques.rinhaBackEnd23.service;

import com.sousajaques.rinhaBackEnd23.models.Usuarios;
import com.sousajaques.rinhaBackEnd23.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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


    /** Uma lista de usuários com {@link Collections#synchronizedList(List)}
     *
     * <p>Esse método evita a corrupção dos dados, por exemplo, se duas threads tentarem modificar o buffer com os usuários</p>
     *
     * <p>Isso bloqueia o acesso de outras threads enquanto uma está lendo ou escrevendo dados.</p>
     * **/
    private final List<Usuarios> buffer = Collections.synchronizedList(new ArrayList<>());



    // Guardando usuários na lista
    public void bufferUsuarios(Usuarios usuarios){
        buffer.add(usuarios);
    }


    /** {@link Scheduled#fixedRate()} roda um método a cada milissegundos
     *
     * <p> A cada 50ms salva em lote os usuários no banco, isso diminui drasticamente as conexões por requisição no banco de dados</p>
     *
     * <p> Obs: "{@code synchronized (buffer)} impede que o buffer seja acessado enquanto nenhuma thread esteja mexendo" </p>
     *
     * <p> Precisa ser garantida que mesmo com o método <pre>{@code synchronizedList()}</pre> Os dados são copiados e armazenados e esvazia o buffer quando ninguém mexe</p>
     * **/
    @Scheduled(fixedRate = 50)
    public void salvarEmLote(){

        List<Usuarios> lote;

        //Copia os dados e esvazia o buffer quando nenhuma thread está acessando
        synchronized (buffer){

                if (buffer.isEmpty()){
                   return;
                }

                lote = new ArrayList<>(buffer);
                buffer.clear();
        }

        usuariosRepository.saveAll(lote); //inserindo o lote de uma vez
    }



    /** Listar {@link Usuarios} pelo Id
     *
     * <p> Caso não haja retorna nulo para lidar com a excessão no Controller</p>**/
    public Usuarios listarPorId(UUID id){
        Optional<Usuarios> usuarioId = usuariosRepository.findById(id);

        return usuarioId.orElse(null);
    }


}
