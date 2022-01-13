package com.onlineshop.product_service;

import com.onlineshop.product_service.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//@FeignClient("product-service")
@FeignClient(name = "product-service", url = "http://192.168.100.174:9000")
public interface ICustomerServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/products")
    CollectionModel<Product> getProducts();

}
