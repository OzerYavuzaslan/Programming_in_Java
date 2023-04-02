package com.ozeryavuzaslan.webservices.restfulwebservicesProject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Restful Web Services Project API", version = "1.0", description = "RestfulWebServicesProject API Information"))
public class RestfulWebServicesProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesProjectApplication.class, args);
	}
}
