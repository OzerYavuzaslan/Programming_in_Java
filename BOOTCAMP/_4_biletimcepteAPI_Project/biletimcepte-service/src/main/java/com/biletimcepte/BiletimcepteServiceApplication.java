package com.biletimcepte;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Biletim Cepte API", version = "1.0", description = "BiletimcepteServiceApplication API Information"))
public class BiletimcepteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiletimcepteServiceApplication.class, args);
    }
}