package com.onlineshop.product_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProdcutQuantityMustNotBeNegative extends ResponseStatusException {

    public ProdcutQuantityMustNotBeNegative(int quantity) {
        super(HttpStatus.NOT_FOUND, getExceptionMessage(quantity));
    }

    private static String getExceptionMessage(int quantity) {
        return String.format("The quantity of the product must not be negative! QUANTITY: %d", quantity);
    }

}