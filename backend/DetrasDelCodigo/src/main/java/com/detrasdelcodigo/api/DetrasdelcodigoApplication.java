package com.detrasdelcodigo.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.detrasdelcodigo.api.upload.StorageService;

@SpringBootApplication
@EnableJpaAuditing
public class DetrasdelcodigoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetrasdelcodigoApplication.class, args);
	}



	@Bean
	public CommandLineRunner init(StorageService storageService,Environment env) {
		return args -> {
			// Inicializamos el servicio de ficheros

//			if (env.getProperty("spring.profiles.active").equalsIgnoreCase("dev")) {
//				storageService.deleteAll();
//			}

			storageService.init();

		};

	}

}
