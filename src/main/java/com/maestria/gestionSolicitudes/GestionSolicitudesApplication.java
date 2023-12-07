package com.maestria.gestionSolicitudes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.maestria.gestionSolicitudes.repository")
@EnableFeignClients
public class GestionSolicitudesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionSolicitudesApplication.class, args);
	}

}
