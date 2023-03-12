package com.emlakcepte;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Emlakcepte Payment API", version = "1.0", description = "Payment API Information"))
@EnableEurekaClient
public class EmlakceptePaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmlakceptePaymentServiceApplication.class, args);
    }

}
