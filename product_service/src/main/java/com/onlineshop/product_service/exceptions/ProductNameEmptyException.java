package com.onlineshop.product_service.exceptions;

import com.onlineshop.product_service.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductNameEmptyException extends ResponseStatusException {

    public ProductNameEmptyException(Product product) {
        super(HttpStatus.BAD_REQUEST, getExceptionMessage(product));
    }

    private static String getExceptionMessage(Product product) {
        return product.getId() != null ? String.format("The products name must not be empty! Product-ID: %d", product.getId()) : "The products name must not be empty!";
    }


}
