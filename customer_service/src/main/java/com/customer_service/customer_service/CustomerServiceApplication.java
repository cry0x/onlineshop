package com.customer_service.customer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.data.mongodb.repository.config.EnableMongoRepositorys;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
// See: localhost:9001/swagger-ui.html
// Get JSON: http://localhost:9001/v3/api-docs
// @EnableMongoRepositories
@OpenAPIDefinition
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
