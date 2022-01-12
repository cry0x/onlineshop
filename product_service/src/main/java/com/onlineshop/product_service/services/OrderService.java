package com.onlineshop.product_service.services;

import com.onlineshop.product_service.entities.Product;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public OrderService() {
    }

    public boolean checkProductInOrder(Product product) {
        System.out.println("MOCKED: PRODCUT EXISTS IN ORDER");
        return true;
    }
}
