package com.sousajaques.rinhaBackEnd23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Agendamento para inserção de usuários em lote
public class RinhaBackEnd23Application {

	public static void main(String[] args) {
		SpringApplication.run(RinhaBackEnd23Application.class, args);
	}

}
