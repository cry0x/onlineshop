package com.onlineshop.order_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "product-service")
public interface IProductServiceClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "/v1/products/{productId}") // CHECK RIGHT PATH
    Long getProductQuantity(@PathVariable("productId") Long productId);

}
