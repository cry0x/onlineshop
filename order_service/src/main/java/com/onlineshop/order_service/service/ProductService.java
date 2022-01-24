package com.onlineshop.order_service.service;

/*
import com.onlineshop.order_service.clients.IProductServiceClient;
import com.onlineshop.order_service.controller.OrderController;
import com.onlineshop.order_service.dto.ProductDto;
import com.onlineshop.order_service.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final IProductServiceClient iProductServiceClient;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private ProductDto product;

    @Autowired
    public ProductService(IProductServiceClient iProductServiceClient) {
        this.iProductServiceClient = iProductServiceClient;
    }

    // Checks in Product Service if there's sufficient quantity of the requested product available
    public ProductDto sufficientProductQuantity(Product newProduct) {
        try {
            product = this.iProductServiceClient.sufficientProductQuantity(newProduct.getOriginalId(), newProduct.getQuantity());
            System.out.println("Product is: " + product);
        } catch (Exception e) {
            log.error("Product service is not available!\n" + e.getLocalizedMessage());
        }

        return product;
    }

}

 */