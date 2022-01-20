package com.onlineshop.customer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

/**
 * Main Class of the customer_service Microservice.
 * See:         localhost:8080/swagger-ui.html
 * Get JSON:    http://localhost:8080/v3/api-docs
 * Launch:      docker compose up --build
 */
@SpringBootApplication
@OpenAPIDefinition
@EnableEurekaClient
@EnableFeignClients
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
