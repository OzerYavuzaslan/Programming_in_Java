package com.ozeryavuzaslan.organizationservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Organization Service API",
				version = "1.0",
				contact = @Contact(
						name = "Özer",
						email = "ozeryavuzaslan@yahoo.com",
						url = "https://www.linkedin.com/in/%C3%B6zer-yavuzaslan-29169315b/"
				),
				description = "RestfulWebServicesProject API Information"
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Organization Management Documentation",
				url = "https://www.javaguides.net/user_management.html"
		)
)
public class OrganizationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrganizationServiceApplication.class, args);
	}
}
