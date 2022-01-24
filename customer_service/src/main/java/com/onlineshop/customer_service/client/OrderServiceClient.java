package com.onlineshop.customer_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Interface to implement the Feign Client.
 * Enables the User to send Requests to differen Microservices.
 * @author Nico Welsch
 * @version 1.0
 */
@FeignClient(name = "order-service")
public interface OrderServiceClient {

    /**
     * Checks if a customer has outstanding orders.
     * @param customerId    Specifies the customer
     * @return  true or false
     */
    @RequestMapping(method = RequestMethod.GET, value = "/v1/orders/{customerId}/orders")
    boolean getCustomerOrders(@PathVariable("customerId") Long customerId);

}
