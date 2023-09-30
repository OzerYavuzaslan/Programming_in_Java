package com.ozeryavuzaslan.revenueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RevenueServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RevenueServiceApplication.class, args);
	}
}
