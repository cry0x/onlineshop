package com.onlineshop.product_service.services;

import com.onlineshop.product_service.clients.IOrderServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private final IOrderServiceClient iOrderServiceClient;

    @Autowired
    public OrderService(IOrderServiceClient iOrderServiceClient) {
        this.iOrderServiceClient = iOrderServiceClient;
    }

    public boolean existsProductInOrder(Long productId) {
        return this.iOrderServiceClient.getIsProductInOrders(productId);
    }
}
