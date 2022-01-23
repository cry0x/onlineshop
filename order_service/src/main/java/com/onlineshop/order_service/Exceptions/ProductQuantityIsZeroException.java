package com.onlineshop.order_service.Exceptions;

import com.onlineshop.order_service.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductQuantityIsZeroException extends ResponseStatusException {

    public ProductQuantityIsZeroException(Product product) {
        super(HttpStatus.BAD_REQUEST, String.format("The quantity of a product (product id: %d) that is added to an order can't be 0!", product.getOriginalId()));
    }
}
