package com.emlakcepte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EmlakcepteLoggerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmlakcepteLoggerServiceApplication.class, args);
    }

}
