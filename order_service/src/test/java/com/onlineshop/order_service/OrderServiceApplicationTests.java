package com.onlineshop.order_service;

import com.onlineshop.order_service.controller.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest({"eureka.client.enabled:false"})
class OrderServiceApplicationTests {

	@Autowired
	private OrderController orderController;

	@Test
	void contextLoads() {
		assertNotNull(orderController);
	}

}
