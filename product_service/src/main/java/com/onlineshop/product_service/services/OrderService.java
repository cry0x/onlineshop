package com.onlineshop.product_service.services;

import com.onlineshop.product_service.clients.IOrderServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private final IOrderServiceClient iOrderServiceClient;
    private final static Logger log = LogManager.getLogger(OrderService.class.getName());

    @Autowired
    public OrderService(IOrderServiceClient iOrderServiceClient) {
        this.iOrderServiceClient = iOrderServiceClient;
    }

    public boolean existsProductInOrder(Long productId) {
        boolean retValue = false;
        try {
            retValue = this.iOrderServiceClient.getIsProductInOrders(productId);
        } catch (Exception e) {
            log.error("Order-Service is not available!\n" + e.getLocalizedMessage());
        }
        return retValue;
    }
}
