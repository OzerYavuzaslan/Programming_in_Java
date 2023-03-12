package com.biletimcepte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BiletimcepteEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiletimcepteEurekaServerApplication.class, args);
    }
}