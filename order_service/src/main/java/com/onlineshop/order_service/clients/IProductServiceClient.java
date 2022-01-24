package com.onlineshop.order_service.clients;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Interface that is used for consuming API calls, using FeignClient.
 * The appropriate is provided by the Eureka server.
 * @author Simon Spang
 */
@FeignClient(name = "product-service")
public interface IProductServiceClient {

    /**
     * Addresses the product service API to check if there is a sufficient amount of a product left in stock
     * before adding it to an order.
     *
     * @param productId Identifier of the above described product
     * @return TODO
     */
/*
    @RequestMapping(method = RequestMethod.GET, value = "/v1/products/{productId}/{amount}")
    ProductDto sufficientProductQuantity(@PathVariable("productId") Long productId, @PathVariable("amount") Long amount);
*/
}
