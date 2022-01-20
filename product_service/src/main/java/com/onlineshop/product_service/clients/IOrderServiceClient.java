package com.onlineshop.product_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "order-service-dev")
public interface IOrderServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/orders/product/{productId}")
    boolean getIsProductInOrders(@PathVariable("productId") Long productId);

}
