package com.emlakcepte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EmlakcepteRealtyStatusServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmlakcepteRealtyStatusServiceApplication.class, args);
    }
}
