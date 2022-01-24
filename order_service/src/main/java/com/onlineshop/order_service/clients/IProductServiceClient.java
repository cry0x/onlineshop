package com.onlineshop.order_service.clients;

import com.onlineshop.order_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    @RequestMapping(method = RequestMethod.GET, value = "/v1/products/{productId}/{amount}")
    ProductDto sufficientProductQuantity(@PathVariable("productId") Long productId, @PathVariable("amount") Long amount);

}
