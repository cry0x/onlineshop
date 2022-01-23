package com.onlineshop.product_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductDoesntExistsException extends ResponseStatusException {

    public ProductDoesntExistsException(Long productId) {
        super(HttpStatus.NOT_FOUND, getExceptionMessage(productId));
    }

    private static String getExceptionMessage(Long productId) {
        return String.format("The product with the ID: %d doesnt exist!", productId);
    }

}
