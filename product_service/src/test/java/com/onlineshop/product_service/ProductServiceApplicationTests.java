package com.onlineshop.product_service;

import com.onlineshop.product_service.controllers.ProductController;
import com.onlineshop.product_service.controllers.ProductPictureController;
import com.onlineshop.product_service.repositories.IProductPictureRepository;
import com.onlineshop.product_service.repositories.IProductRepository;
import com.onlineshop.product_service.services.OrderService;
import com.onlineshop.product_service.services.ProductPictureService;
import com.onlineshop.product_service.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest({"eureka.client.enabled:false"})
class ProductServiceApplicationTests {

    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductController productController;
    @Autowired
    private IProductPictureRepository iProductPictureRepository;
    @Autowired
    private ProductPictureService productPictureService;
    @Autowired
    private ProductPictureController productPictureController;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductServiceApplication productServiceApplication;

    @Test
    void contextLoads() {
        assertNotNull(iProductRepository);
        assertNotNull(productService);
        assertNotNull(productController);
        assertNotNull(iProductPictureRepository);
        assertNotNull(productPictureService);
        assertNotNull(productPictureController);
        assertNotNull(orderService);
        assertNotNull(productServiceApplication);
    }
}
