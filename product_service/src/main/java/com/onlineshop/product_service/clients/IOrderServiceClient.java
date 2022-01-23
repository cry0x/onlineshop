package com.onlineshop.product_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This interface represents the API-Calls which we want to consume in our service.
 * To do this we use FeignClient who will get the corresponding address of the service
 * to use from the Eureka-Server.
 */
@FeignClient(name = "order-service")
public interface IOrderServiceClient {

    /**
     * Calls the order-service API to get and wants to know if a Product is still referenced inside an Order.
     *
     * @param productId Identifier of the Product which should be checked
     * @return If a Product is still referenced it will return ture else false
     */
    @RequestMapping(method = RequestMethod.GET, value = "/v1/orders/products/{productId}")
    boolean getIsProductInOrders(@PathVariable("productId") Long productId);

}
