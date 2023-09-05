package com.ozeryavuzaslan.stockservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class StockServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}
}
