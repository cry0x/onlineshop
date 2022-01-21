package com.onlineshop.customer_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.onlineshop.customer_service.controller.CustomerController;
import com.onlineshop.customer_service.repository.CustomerRepository;
import com.onlineshop.customer_service.service.CustomerService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
/**
 * Tests if the vital components of the order_service are available.
 * @author Nico Welsch
 * @version 1.0
 */
@SpringBootTest({"eureka.client.enabled:false"})
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
