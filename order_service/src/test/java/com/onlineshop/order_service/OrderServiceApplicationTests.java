package com.onlineshop.order_service;

import com.onlineshop.order_service.controller.OrderController;
import com.onlineshop.order_service.repository.IOrderRepository;
import com.onlineshop.order_service.repository.IProductRepository;
import com.onlineshop.order_service.service.EmailService;
import com.onlineshop.order_service.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest({"eureka.client.enabled:false"})
class OrderServiceApplicationTests {

    @Autowired
    private IOrderRepository iOrderRepository;
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private OrderController orderController;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderServiceApplication orderServiceApplication;
    @Autowired
    private EmailService emailService;

    @Test
    void contextLoads() {
        assertNotNull(iOrderRepository);
        assertNotNull(iProductRepository);
        assertNotNull(orderController);
        assertNotNull(orderService);
        assertNotNull(orderServiceApplication);
        assertNotNull(emailService);
    }

}
