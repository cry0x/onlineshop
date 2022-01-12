package com.onlineshop.product_service.services;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public OrderService() {
    }

    public boolean checkArticleInOrder(Long articleId) {
        return true;
    }
}
