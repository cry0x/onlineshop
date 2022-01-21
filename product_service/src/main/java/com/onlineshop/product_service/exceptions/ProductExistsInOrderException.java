package com.onlineshop.product_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductExistsInOrderException extends ResponseStatusException {
    public ProductExistsInOrderException(Long productId) {
        super(HttpStatus.BAD_REQUEST, getExceptionMessage(productId));
    }

    private static String getExceptionMessage(Long productId) {
        return String.format("The product with the ID: %d still exists in an order!", productId);
    }

}
