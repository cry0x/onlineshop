package com.onlineshop.customer_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/orders/{id}/orders")
    boolean getCustomerOrders(@PathVariable("customerId") Long customerId);

}
