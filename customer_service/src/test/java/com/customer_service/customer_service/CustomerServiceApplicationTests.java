package com.customer_service.customer_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.customer_service.customer_service.controller.CustomerController;
import com.customer_service.customer_service.repository.CustomerRepository;
import com.customer_service.customer_service.service.CustomerService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

// @SpringBootTest
class CustomerServiceApplicationTests {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

	@Test
	void contextLoads() {
        assertNotNull(customerController);
        assertNotNull(customerRepository);
        assertNotNull(customerService);
	}

}
