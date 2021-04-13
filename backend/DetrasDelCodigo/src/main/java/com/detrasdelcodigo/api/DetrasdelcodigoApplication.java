package com.detrasdelcodigo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DetrasdelcodigoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetrasdelcodigoApplication.class, args);
	}

}
