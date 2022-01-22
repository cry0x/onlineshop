package com.onlineshop.order_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


//@EnableEurekaClient TODO
@SpringBootApplication
@EnableConfigurationProperties
@OpenAPIDefinition
@EntityScan(basePackages = {"com.onlineshop.order_service"})
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
